package com.social.network.validation;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.FriendDao;
import com.social.network.domain.dao.GroupDao;
import com.social.network.domain.dao.MessageDao;
import com.social.network.domain.dao.UsersDao;
import com.social.network.domain.exceptions.hibernate.GenericDaoException;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.exceptions.chat.ChatNotExistException;
import com.social.network.exceptions.chat.EmptyMessageException;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.group.GroupPermissionExceptions;
import com.social.network.exceptions.user.UserNotExistException;

/**
 * Created by Yadykin Andrii Jun 3, 2016
 *
 */

public class DaoValidation {

    private final static Logger logger = LoggerFactory.getLogger(DaoValidation.class);

    public static User userExistValidation(UsersDao usersDao, long userId) {
        logger.debug("-> userExistValidation: userId: {}", userId);
        try {
            return usersDao.load(userId);
        } catch (GenericDaoException e) {
            throw new UserNotExistException("User with id " + userId + "don't exist");
        }
    }

    public static Friend friendExistValidation(FriendDao friendDao, long friendId) {
        logger.debug("-> friendExistValidation: friendId: {}", friendId);
        Friend friend = friendDao.get(friendId);
        if (Objects.isNull(friend)) {
            throw new FriendNotExistException("Friend with id " + friendId + "don't exist");
        }
        return friend;
    }

    public static Group groupExistValidation(GroupDao groupDao, long groupId) {
        logger.debug("-> groupExistValidation: groupId: {}", groupId);
        Group group = groupDao.get(groupId);
        if (Objects.nonNull(group)) {
            return group;
        } else {
            throw new GroupPermissionExceptions("Group with ID:" + groupId + " don't exist");
        }
    }

    public static Message messageExistValidation(MessageDao messageDao, long messageId) {
        logger.debug("-> messageExistValidation:  messageId: {}", messageId);
        try {
            return messageDao.load(messageId);
        } catch (GenericDaoException e) {
            throw new EmptyMessageException("Message with ID:" + messageId + " don't exist");
        }
    }

    public static Chat chatExistValidation(ChatDao chatDao, long chatId) {
        logger.debug("-> chatExistValidation:  chatId: {}", chatId);
        Chat chat = chatDao.get(chatId);
        if (Objects.isNull(chat)) {
            throw new ChatNotExistException("Chat with ID:" + chatId + " don't exist");
        }
        return chat;
    }
}
