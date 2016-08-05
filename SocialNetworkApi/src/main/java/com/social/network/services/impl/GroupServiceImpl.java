package com.social.network.services.impl;

import static com.social.network.utils.Constants.ADD_USER_TO_GROUP_MESSAGE;
import static com.social.network.utils.Constants.CREATE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_USER_FROM_GROUP_MESSAGE;
import static com.social.network.utils.Constants.LEAVE_GROUP_MESSAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.ChatDao;
import com.social.network.dao.FriendDao;
import com.social.network.dao.GroupDao;
import com.social.network.dao.UserChatDao;
import com.social.network.dto.FriendDto;
import com.social.network.exceptions.group.GroupAdminException;
import com.social.network.exceptions.group.GroupPermissionExceptions;
import com.social.network.message.Subscribers;
import com.social.network.message.builder.MessageBuilder;
import com.social.network.message.system.impl.GroupMessage;
import com.social.network.model.Chat;
import com.social.network.model.Friend;
import com.social.network.model.Group;
import com.social.network.model.Message;
import com.social.network.model.User;
import com.social.network.model.enums.FriendStatus;
import com.social.network.services.FriendService;
import com.social.network.services.GroupService;
import com.social.network.services.UserService;
import com.social.network.utils.EntityToDtoMapper;
import com.social.network.validation.DaoValidation;

/**
 * Created by andrii.perylo on 5/17/2016.
 */
@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private ChatDao chatDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserChatDao userChatDao;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private MessageBuilder messageBuilder;
    @Autowired
    private GroupMessage groupMessageBuilder;

    @Override
    @Transactional(readOnly = true)
    public List<Group> getGroups() {
        // Get logged user
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> getGroups for user : {}", loggedUser.getUserId());

        return groupDao.findByOwner(loggedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Group getGroup(long groupId) {
        // Get loggedUser
        long userId = userService.getLoggedUserId();
        logger.debug("getGroup: groupId: {}", groupId);

        // Validation
        isUserInGroup(userId, groupId, true);

        return DaoValidation.groupExistValidation(groupDao, groupId);
    }

    @Override
    @Transactional
    public Group createGroup(String name, String[] userIds) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("createGroup : name = {}, adminId  = {}", name, loggedUser.getUserId());

        // TODO Validate group name

        // Create user list
        List<User> usersList = new ArrayList<>();
        usersList.add(loggedUser);
        if (Objects.nonNull(userIds)) {
            int size = userIds.length;
            for (int i = 0; i < size; ++i) {
                User user = userService.getUserById(Long.valueOf(userIds[i]));
                friendService.validateFriendByStatus(user, loggedUser, FriendStatus.ACCEPTED);
                usersList.add(user);
            }
        }

        // Create group
        Group group = groupDao.merge(new Group(name, loggedUser.getUserId(), usersList));

        // Create messages
        Subscribers subscribers = new Subscribers(loggedUser, usersList);
        for (User user : usersList) {
            if (user.getUserId() == loggedUser.getUserId()) {
                messageBuilder.setMessageBuilder(groupMessageBuilder).createOneParamMessage(CREATE_GROUP_MESSAGE, subscribers, group);
            } else {
                messageBuilder.setMessageBuilder(groupMessageBuilder).createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE, user, subscribers,
                        group);
            }
        }

        return group;
    }

    @Override
    @Transactional
    public Message addUserToGroup(long groupId, long invitedUserId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> addUserToGroup : groupId = {}, UserId = {}, invitedUserId = {}", groupId, loggedUser.getUserId(), invitedUserId);

        // Get group
        Group group = DaoValidation.groupExistValidation(groupDao, groupId);
        User invitedUser = userService.getUserById(invitedUserId);

        // Validation
        //addUserToGroupValidation(loggedUser, invitedUser, group);

        Chat chat = group.getChat();
        chat.addUser(invitedUser);
        chatDao.saveOrUpdate(chat);

        // Create message
        Subscribers subscribers = new Subscribers(loggedUser, chat.getUsers());
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE,
                invitedUser, subscribers, chat);

        return message;
    }

    @Override
    @Transactional
    public Message deleteUserFromGroup(long groupId, long removedUserId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> deleteUserFromGroup : groupId = {}, UserId = {}", groupId, loggedUser.getUserId());

        // Get group
        Group group = DaoValidation.groupExistValidation(groupDao, groupId);
        User removedUser = userService.getUserById(removedUserId);

        // Validation
        deleteUserFromGroupValidation(loggedUser, removedUser, group);

        // Get chat
        Chat chat = group.getChat();

        // Remove user from chat
        removeUserFromChat(removedUser, chat);

        // Create message
        Subscribers subscribers = new Subscribers(loggedUser, chat.getUsers());
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createTwoParamsMessage(DELETE_USER_FROM_GROUP_MESSAGE,
                removedUser, subscribers, chat);

        return message;
    }

    @Override
    @Transactional
    public Message leaveGroup(long groupId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("leaveGroup : groupId = {}, userId = {}", groupId, loggedUser.getUserId());

        Group group = DaoValidation.groupExistValidation(groupDao, groupId);

        // Validation
        leaveGroupValidation(loggedUser, group);

        // Get chat
        Chat chat = group.getChat();

        // Remove user from chat
        removeUserFromChat(loggedUser, chat);
        // Create message
        Subscribers subscribers = new Subscribers(loggedUser, chat.getUsers());
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createOneParamMessage(LEAVE_GROUP_MESSAGE, subscribers,
                chat);

        return message;
    }

    @Override
    @Transactional
    public Message deleteGroup(long groupId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> deleteGroup : groupId: {}, userId: {}", groupId, loggedUser.getUserId());

        Group group = DaoValidation.groupExistValidation(groupDao, groupId);

        // Validation
        deleteGroupValidation(loggedUser, group);

        // Get chat
        Chat chat = group.getChat();
        // Create message
        Subscribers subscribers = new Subscribers(loggedUser, chat.getUsers());
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createOneParamMessage(DELETE_GROUP_MESSAGE, subscribers,
                chat);

        chat.hiddeChat();
        groupDao.saveOrUpdate(group);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendDto> getFriendsNotInGroup(long groupId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> getNotGroupFriends: userId: {}, groupId: {}", loggedUser.getUserId(), groupId);

        isUserInGroup(loggedUser.getUserId(), groupId, true);

        Group group = DaoValidation.groupExistValidation(groupDao, groupId);

        
        //group.getUsers();
        
        // Fill FriendsDto list
        List<FriendDto> friendDtoList = new ArrayList<>();
        for (Friend friend : friendDao.findFriendNotInGroup(group, loggedUser)) {
            friendDtoList.add(EntityToDtoMapper.convertFriendToFriendDto(friend));
        }
        return friendDtoList;
    }

    @Transactional
    private void removeUserFromChat(User user, Chat chat) {
        userChatDao.removeUserFromChat(chat, user);
    }

    /*
     * Custom action validation
     */

    private void addUserToGroupValidation(User loggedUser, User invitedUser, Group group) {
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), true);
        isUserInGroup(invitedUser.getUserId(), group.getChatId(), false);
        isYourFriend(loggedUser, invitedUser);
    }

    private void deleteUserFromGroupValidation(User loggedUser, User deletedUser, Group group) {
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), true);
        if (loggedUser.getUserId() == deletedUser.getUserId()) {
            throw new GroupAdminException("You are group admin! You can't delete himself");
        }
        isUserInGroup(deletedUser.getUserId(), group.getChatId(), true);
    }

    private void leaveGroupValidation(User loggedUser, Group group) {
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), false);
        isUserInGroup(loggedUser.getUserId(), group.getChatId(), true);
    }

    private void deleteGroupValidation(User loggedUser, Group group) {
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), true);
    }

    /*
     * General validation
     */

    private void isAdminAction(long userId, long adminId, boolean adminAction) {
        logger.debug("-> isUserGroupAdmin ");

        if ((userId != adminId) == adminAction) {
            throw new GroupAdminException("This is admin action. You are not group admin!");
        } else if ((userId == adminId) != adminAction) {
            throw new GroupAdminException("This isn't admin action. You are group admin!");
        }
    }

    private void isUserInGroup(long userId, long groupId, boolean userStatus) {
        logger.debug("-> isUserAction: groupId: {}, userId: {}", groupId, userId);

        boolean result = true;// userGroupDao.findByGroupAndUser(groupId,
                              // userId);

        if (!result && userStatus) {
            throw new GroupPermissionExceptions("You are not in this group");
        } else if (result && !userStatus) {
            throw new GroupPermissionExceptions("User already in this group");
        }

    }

    private void isYourFriend(User loggedUser, User invitedUser) {
        friendService.validateFriendByStatus(invitedUser, loggedUser, FriendStatus.ACCEPTED);
    }

}
