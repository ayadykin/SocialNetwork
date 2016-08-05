package com.social.network.validation;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.dao.ChatDao;
import com.social.network.dao.FriendDao;
import com.social.network.dao.GroupDao;
import com.social.network.dao.MessageDao;
import com.social.network.dao.UsersDao;
import com.social.network.exceptions.chat.ChatNotExistException;
import com.social.network.exceptions.chat.EmptyMessageException;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.group.GroupPermissionExceptions;
import com.social.network.exceptions.user.UserNotExistException;
import com.social.network.model.Chat;
import com.social.network.model.Friend;
import com.social.network.model.Group;
import com.social.network.model.Message;
import com.social.network.model.User;

/**
 * Created by Yadykin Andrii Jun 3, 2016
 *
 */

public class DaoValidation {

    private final static Logger logger = LoggerFactory.getLogger(DaoValidation.class);

    public static User userExistValidation(UsersDao usersDao, long userId) {
        logger.debug("-> userExistValidation: userId: {}", userId);
        User user = usersDao.get(userId);
        if (Objects.isNull(user)) {
            throw new UserNotExistException("User with id " + userId + "don't exist");
        }
        return user;
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
        if (!groupDao.exists(groupId)) {
            throw new GroupPermissionExceptions("Group with ID:" + groupId + " don't exist");
        }
        return groupDao.get(groupId);
    }

    public static Message messageExistValidation(MessageDao messageDao, long messageId) {
        logger.debug("-> messageExistValidation:  messageId: {}", messageId);
        if (!messageDao.exists(messageId)) {
            throw new EmptyMessageException("Message with ID:" + messageId + " don't exist");
        }
        return messageDao.get(messageId);
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
