package com.social.network.services.impl;

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

import com.social.network.core.GroupModel;
import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.GroupDao;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.exceptions.group.DeleteGroupException;
import com.social.network.exceptions.group.GroupAdminException;
import com.social.network.exceptions.group.GroupPermissionExceptions;
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

        Group group = DaoValidation.groupExistValidation(groupDao, groupId);
        // Validation
        isUserInGroup(userId, group.getChatId(), true);

        return group;
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
                if (Objects.nonNull(userIds[i])) {
                    User user = userService.getUserById(Long.valueOf(userIds[i]));
                    isYourFriend(loggedUser, user);
                    usersList.add(user);
                }
            }
        }

        // Create friend
        Chat chat = chatDao.merge(new Chat());

        // Create group
        Group group = groupDao.merge(new Group(chat, name, loggedUser));

        // Create messages
        for (User user : usersList) {
            // Set chat name
            chat.addUserChat(userChatDao.merge(new UserChat(chat, user, name)));
        }

        return group;
    }

    @Override
    @Transactional
    public GroupModel addUserToGroup(long groupId, long invitedUserId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> addUserToGroup : groupId = {}, UserId = {}, invitedUserId = {}", groupId, loggedUser.getUserId(), invitedUserId);

        // Get group
        Group group = DaoValidation.groupExistValidation(groupDao, groupId);
        User invitedUser = userService.getUserById(invitedUserId);

        // Validation
        addUserToGroupValidation(loggedUser, invitedUser, group);

        group.getChat().addUserChat(userChatDao.merge(new UserChat(group.getChat(), invitedUser, group.getGroupName())));

        return new GroupModel(invitedUser, loggedUser, group);
    }

    @Override
    @Transactional
    public GroupModel deleteUserFromGroup(long groupId, long removedUserId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> deleteUserFromGroup : groupId = {}, UserId = {}", groupId, loggedUser.getUserId());

        // Get group
        Group group = DaoValidation.groupExistValidation(groupDao, groupId);
        User removedUser = userService.getUserById(removedUserId);

        // Validation
        deleteUserFromGroupValidation(loggedUser, removedUser, group);

        userChatDao.removeUserFromChat(group.getChat(), removedUser);

        return new GroupModel(removedUser, loggedUser, group);
    }

    @Override
    @Transactional
    public GroupModel leaveGroup(long groupId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("leaveGroup : groupId = {}, userId = {}", groupId, loggedUser.getUserId());

        Group group = DaoValidation.groupExistValidation(groupDao, groupId);

        // Validation
        leaveGroupValidation(loggedUser, group);

        Chat chat = group.getChat();

        userChatDao.removeUserFromChat(chat, loggedUser);

        return new GroupModel(null, loggedUser, group);
    }

    @Override
    @Transactional
    public GroupModel deleteGroup(long groupId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("-> deleteGroup : groupId: {}, userId: {}", groupId, loggedUser.getUserId());

        Group group = DaoValidation.groupExistValidation(groupDao, groupId);

        // Validation
        deleteGroupValidation(loggedUser, group);

        Chat chat = group.getChat();
        chat.hiddeChat();
        group.hiddeGroup();
        return new GroupModel(null, loggedUser, group);
    }

    /*
     * Custom action validation
     */

    private void addUserToGroupValidation(User loggedUser, User invitedUser, Group group) {
        logger.debug("-> addUserToGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdmin().getUserId(), true);
        isUserInGroup(invitedUser.getUserId(), group.getChatId(), false);
        isYourFriend(loggedUser, invitedUser);
    }

    private void deleteUserFromGroupValidation(User loggedUser, User deletedUser, Group group) {
        logger.debug("-> deleteUserFromGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdmin().getUserId(), true);
        if (loggedUser.getUserId() == deletedUser.getUserId()) {
            throw new GroupAdminException("You are group admin! You can't delete himself");
        }
        isUserInGroup(deletedUser.getUserId(), group.getChatId(), true);
        isGroupRemoved(group);
    }

    private void leaveGroupValidation(User loggedUser, Group group) {
        logger.debug("-> leaveGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdmin().getUserId(), false);
        isUserInGroup(loggedUser.getUserId(), group.getChatId(), true);
        isGroupRemoved(group);
    }

    private void deleteGroupValidation(User loggedUser, Group group) {
        logger.debug("-> deleteGroupValidation ");
        isAdminAction(loggedUser.getUserId(), group.getAdmin().getUserId(), true);
        isGroupRemoved(group);
    }

    /*
     * General validation
     */
    private void isGroupRemoved(Group group) {
        if (group.isHidden()) {
            throw new DeleteGroupException("The group has already removed!");
        }
    }

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
        try {
            friendService.validateFriendByStatus(invitedUser, loggedUser, FriendStatus.ACCEPTED);
        } catch (RuntimeException e) {
            throw new GroupPermissionExceptions("You catn't add this user!");
        }
    }

}
