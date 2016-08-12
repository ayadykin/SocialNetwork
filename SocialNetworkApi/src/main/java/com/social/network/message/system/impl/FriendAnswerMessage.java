package com.social.network.message.system.impl;

import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.exceptions.MessageException;
import com.social.network.message.system.SystemMessageStrategy;
import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.SystemMessage;
import com.social.network.model.User;
import com.social.network.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Service
public class FriendAnswerMessage extends SystemMessageStrategy {

    private static final Logger logger = LoggerFactory.getLogger(FriendAnswerMessage.class);

    @Override
    @Transactional
    public Message createMessage(String messageTemplate, String[] params, User publisher, Set<User> subscribers, Chat chat) {
        logger.debug("-> createMessage chatId : " + chat.getChatId());

        SystemMessage systemMessage = (SystemMessage) chat.getMessages().iterator().next();

        if (Objects.nonNull(systemMessage) && systemMessage.getSystemMessageStatus() == SystemMessageStatus.INVITE) {
            systemMessage.setStatusSystem();
        } else {
            throw new MessageException("SystemMessage doesn't exist ");
        }

        return messageSourceBuilder.createMessage(messageTemplate, params, publisher, subscribers, chat, SystemMessageStatus.SYSTEM);
    }

}
