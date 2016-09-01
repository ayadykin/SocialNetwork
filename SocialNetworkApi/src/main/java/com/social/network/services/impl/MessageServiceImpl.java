package com.social.network.services.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.MessageDao;
import com.social.network.domain.dao.RecipientDao;
import com.social.network.domain.dao.SystemMessageDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.Recipient;
import com.social.network.domain.model.SystemMessage;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.exceptions.chat.EditMessageException;
import com.social.network.redis.RedisMessageObserver;
import com.social.network.services.MessageService;
import com.social.network.services.UserService;
import com.social.network.validation.DaoValidation;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Service
public class MessageServiceImpl implements MessageService, RedisMessageObserver {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private SystemMessageDao systemMessageDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private RecipientDao recipientDao;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Message createMessage(String messageText, User publisher, Chat chat) {
        logger.debug("createMessage :  messageText = {}, chat = {}", messageText, chat);
        Message message = messageDao.merge(new Message(messageText, publisher, chat));

        addRecipients(chat.getUserChat(), message.getMessageId());

        return message;
    }

    @Override
    @Transactional
    public Message createSystemMessage(String messageText, User publisher, Chat chat, SystemMessageStatus systemMessageStatus) {
        logger.debug("createMessage :  messageText = {}, chat = {}", messageText, chat);
        SystemMessage message = systemMessageDao.merge(new SystemMessage(messageText, publisher, chat, systemMessageStatus));

        addRecipients(chat.getUserChat(), message.getMessageId());

        return message;
    }

    @Override
    @Transactional
    public Message createMialing(String messageText, User publisher, Set<Chat> chats) {
        SystemMessage message = systemMessageDao.merge(new SystemMessage(messageText, publisher, chats, SystemMessageStatus.SYSTEM));

        //long messageId = message.getMessageId();

        for (Chat chat : chats) {
            addRecipients(chat.getUserChat(), message.getMessageId());
            /*for (UserChat user : chat.getUserChat()) {
                if (user.getUserId() != publisher.getUserId()) {
                    recipientDao.save(new Recipient(user.getUser(), messageId));
                }
            }*/
        }
        return message;
    }

    @Transactional
    private void addRecipients(Set<UserChat> users, long messageId) {
        for (UserChat user : users) {
            recipientDao.save(new Recipient(user.getUser(), messageId));
        }
    }

    @Override
    @Transactional
    public Message editMessage(long messageId, String newMessage) {
        logger.debug("editMessage : messageId = {}, newMessage = {}", messageId, newMessage);
        Message message = DaoValidation.messageExistValidation(messageDao, messageId);
        // Validation
        editMessageValidation(message);
        message.setText(newMessage);
        messageDao.saveOrUpdate(message);
        return message;
    }

    @Override
    @Transactional
    public Message deleteMessage(long messageId) {
        logger.debug("deleteMessage messageId: {}", messageId);
        // TODO Validation
        Message message = DaoValidation.messageExistValidation(messageDao, messageId);
        message.getHidden().setHidden(true);
        messageDao.saveOrUpdate(message);
        return message;
    }

    @Override
    @Transactional
    public boolean updateMessageStatus(long userId, long messageId) {
        for (Recipient recipient : recipientDao.findRecipientsByMessage(messageId)) {
            if (recipient.getUserId() == userId) {
                recipient.setReaded(true);
                recipientDao.save(recipient);
                break;
            }
        }
        return true;
    }

    private void editMessageValidation(Message message) {
        if (message.getHidden().isHidden()) {
            throw new EditMessageException("Message was deleted!");
        }
        User loggedUser = userService.getLoggedUserEntity();
        if (message.getPublisher().getUserId() != loggedUser.getUserId()) {
            throw new EditMessageException("You don't publish this message!");
        }
    }
}
