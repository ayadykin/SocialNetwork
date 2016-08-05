package com.social.network.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.social.network.dao.UserChatDao;
import com.social.network.exceptions.chat.ChatNotExistException;
import com.social.network.model.Chat;
import com.social.network.model.User;
import com.social.network.model.UserChat;
import com.social.network.utils.Constants;

/**
 * Created by Yadykin Andrii Jul 12, 2016
 *
 */
@Repository
public class UserChatDaoImpl implements UserChatDao {
    private static final Logger logger = LoggerFactory.getLogger(UserChatDao.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UserChat findByChatAndUser(long chatId, long userId) {
        logger.debug("-> findByChatAndUser chatId : {}, userId : {}", chatId, userId);
        try {
            return (UserChat) sessionFactory.getCurrentSession().getNamedQuery(Constants.FIND_BY_CHAT_AND_USER)
                    .setLong("chatId", chatId).setLong("userId", userId).uniqueResult();
        } catch (NonUniqueResultException e) {
            logger.debug("findByChatAndUser NonUniqueResultException : {}", e.getMessage());
            throw new ChatNotExistException("Chat with ID:" + chatId + " don't exist");
        } catch (Exception e) {
            logger.debug("findByChatAndUser Exception : {}", e.getMessage());
            throw new ChatNotExistException("Chat with ID:" + chatId + " don't exist");
        }
    }
    
    @Override
    public boolean removeUserFromChat(Chat chat, User user) {
        logger.debug("-> removeGroupAndUser chatId : {}, userId : {}", chat.getChatId(), user.getUserId());
        try {
            return sessionFactory.getCurrentSession().getNamedQuery(Constants.REMOVE_CHAT_AND_USER)
                    .setEntity("chat", chat).setEntity("user", user).executeUpdate() == 1 ;
        } catch (NonUniqueResultException e) {
            logger.debug("removeUserFromChat NonUniqueResultException : {}", e);
            return false;
        } catch (Exception e) {
            logger.debug("removeUserFromChat Exception : {}", e);
            return false;
        }
    }
}
