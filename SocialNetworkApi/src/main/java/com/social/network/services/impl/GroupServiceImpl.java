package com.social.network.services.impl;

import static com.social.network.utils.Constants.ADD_USER_TO_GROUP_MESSAGE;
import static com.social.network.utils.Constants.CREATE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_USER_FROM_GROUP_MESSAGE;
import static com.social.network.utils.Constants.LEAVE_GROUP_MESSAGE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.ChatDao;
import com.social.network.dao.GroupDao;
import com.social.network.dao.UserChatDao;
import com.social.network.dto.GroupUserDto;
import com.social.network.exceptions.group.GroupAdminException;
import com.social.network.exceptions.group.GroupPermissionExceptions;
import com.social.network.message.builder.MessageBuilder;
import com.social.network.message.system.impl.GroupMessage;
import com.social.network.model.Chat;
import com.social.network.model.Group;
import com.social.network.model.Message;
import com.social.network.model.User;
import com.social.network.model.UserChat;
import com.social.network.model.enums.FriendStatus;
import com.social.network.services.FriendService;
import com.social.network.services.GroupService;
import com.social.network.services.UserService;
import com.social.network.validation.DaoValidation;

/**
 * Created by andrii.perylo on 5/17/2016.
 */
@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);
    @Autowired
    private GroupDao groupDao;
    @Autowired
    protected ChatDao chatDao;
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
        Set<User> usersList = new HashSet<>();
        usersList.add(loggedUser);
        if (Objects.nonNull(userIds)) {
            int size = userIds.length;
            for (int i = 0; i < size; ++i) {
                User user = userService.getUserById(Long.valueOf(userIds[i]));
                isYourFriend(loggedUser, user);
                usersList.add(user);
            }
        }

        // Create friend
        Chat chat = chatDao.merge(new Chat(usersList));

        // Create group
        Group group = groupDao.merge(new Group(chat, name, loggedUser.getUserId()));

        // Create messages
        for (User user : usersList) {

            // Set chat name
            UserChat userChat = userChatDao.findByChatAndUser(group.getChatId(), user.getUserId());
            userChat.setChatName(name);
            userChatDao.saveOrUpdate(userChat);
            if (user.getUserId() == loggedUser.getUserId()) {
                messageBuilder.setMessageBuilder(groupMessageBuilder).createOneParamMessage(CREATE_GROUP_MESSAGE, user, usersList, chat);
            } else {
                messageBuilder.setMessageBuilder(groupMessageBuilder).createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE, user, user,
                        usersList, chat);
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
        addUserToGroupValidation(loggedUser, invitedUser, group);

        Chat chat = group.getChat();
        chat.addUser(invitedUser);

        // Set chat name
        UserChat loggedUserChat = userChatDao.findByChatAndUser(group.getChatId(), invitedUser.getUserId());
        loggedUserChat.setChatName(group.getGroupName());

        // Create message
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE,
                invitedUser, loggedUser, chat.getUsers(), chat);

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

        Chat chat = group.getChat();
        chat.removeUser(removedUser);

        // Create message
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createTwoParamsMessage(DELETE_USER_FROM_GROUP_MESSAGE,
                removedUser, loggedUser, chat.getUsers(), chat);

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

        Chat chat = group.getChat();

        // Create message
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createOneParamMessage(LEAVE_GROUP_MESSAGE, loggedUser,
                chat.getUsers(), chat);

        chat.removeUser(loggedUser);

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

        Chat chat = group.getChat();
        chat.hiddeChat();

        // Create message
        Message message = messageBuilder.setMessageBuilder(groupMessageBuilder).createOneParamMessage(DELETE_GROUP_MESSAGE, loggedUser,
                chat.getUsers(), chat);

        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupUserDto> getFriendsNotInGroup(long groupId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> getFriendsNotInGroup: userId: {}, groupId: {}", loggedUser.getUserId(), groupId);

        isUserInGroup(loggedUser.getUserId(), groupId, true);

        Group group = DaoValidation.groupExistValidation(groupDao, groupId);

        Set<User> groupUsers = group.getChat().getUsers();

        logger.debug("-> getFriendsNotInGroup: start stream filter");

        List<User> users = loggedUser.getFriends().stream()
                .filter(p -> p.getFriend() != groupUsers.stream().filter(g -> g != p.getFriend()).iterator().next()
                        && p.getFriendStatus() == FriendStatus.ACCEPTED)
                .map(f -> f.getFriend()).collect(Collectors.toList());

        logger.debug("-> getFriendsNotInGroup: end stream filter");

        // Fill FriendsDto list
        List<GroupUserDto> friendDtoList = new ArrayList<>();
        for (User user : users) {
            friendDtoList.add(new GroupUserDto(user.getUserId(), user.getUserFullName(), false));
        }
        logger.debug("-> getFriendsNotInGroup: end");
        return friendDtoList;
    }

    /*
     * Custom action validation
     */

    private void addUserToGroupValidation(User loggedUser, User invitedUser, Group group) {
        logger.debug("-> addUserToGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), true);
        isUserInGroup(invitedUser.getUserId(), group.getChatId(), false);
        isYourFriend(loggedUser, invitedUser);
    }

    private void deleteUserFromGroupValidation(User loggedUser, User deletedUser, Group group) {
        logger.debug("-> deleteUserFromGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), true);
        if (loggedUser.getUserId() == deletedUser.getUserId()) {
            throw new GroupAdminException("You are group admin! You can't delete himself");
        }
        isUserInGroup(deletedUser.getUserId(), group.getChatId(), true);
    }

    private void leaveGroupValidation(User loggedUser, Group group) {
        logger.debug("-> leaveGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), false);
        isUserInGroup(loggedUser.getUserId(), group.getChatId(), true);
    }

    private void deleteGroupValidation(User loggedUser, Group group) {
        logger.debug("-> deleteGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdminId(), true);
    }

    /*
     * General validation
     */

    private void isAdminAction(long userId, long adminId, boolean adminAction) {
        logger.debug("-> isAdminAction ");

        if ((userId != adminId) == adminAction) {
            throw new GroupAdminException("This is admin action. You are not group admin!");
        } else if ((userId == adminId) != adminAction) {
            throw new GroupAdminException("This isn't admin action. You are group admin!");
        }
    }

    private void isUserInGroup(long userId, long chatId, boolean userStatus) {
        logger.debug("-> isUserInGroup: chatId: {}, userId: {}", chatId, userId);

        UserChat userChat = userChatDao.findByChatAndUser(chatId, userId);

        if (Objects.isNull(userChat) && userStatus) {
            throw new GroupPermissionExceptions("You are not in this group");
        } else if (Objects.nonNull(userChat) && !userStatus) {
            throw new GroupPermissionExceptions("User already in this group");
        }

    }

    private void isYourFriend(User loggedUser, User invitedUser) {
        logger.debug("-> isYourFriend ");
        friendService.validateFriendByStatus(invitedUser, loggedUser, FriendStatus.ACCEPTED);
    }

}
