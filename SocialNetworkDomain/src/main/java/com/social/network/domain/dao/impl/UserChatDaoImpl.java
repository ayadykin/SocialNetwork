package com.social.network.domain.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.exceptions.chat.ChatNotExistException;
import com.social.network.domain.util.Constants;

/**
 * Created by Yadykin Andrii Jul 12, 2016
 *
 */
@Repository
public class UserChatDaoImpl extends GenericDaoHibernate<UserChat, Long> implements UserChatDao {
    private static final Logger logger = LoggerFactory.getLogger(UserChatDao.class);

    public UserChatDaoImpl() {
        super(UserChat.class);
    }

    @Override
    public UserChat findByChatAndUser(long chatId, long userId) {
        logger.debug("-> findByChatAndUser chatId : {}, userId : {}", chatId, userId);
        try {
            return (UserChat) getCurrentSession().getNamedQuery(Constants.FIND_BY_CHAT_AND_USER).setLong("chatId", chatId)
                    .setLong("userId", userId).uniqueResult();
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
            return getCurrentSession().getNamedQuery(Constants.REMOVE_CHAT_AND_USER).setEntity("chat", chat)
                    .setEntity("user", user).executeUpdate() == 1;
        } catch (NonUniqueResultException e) {
            logger.debug("removeUserFromChat NonUniqueResultException : {}", e);
            return false;
        } catch (Exception e) {
            logger.debug("removeUserFromChat Exception : {}", e);
            return false;
        }
    }
}
