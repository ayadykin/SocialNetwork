package com.social.network.core.message.builder.system.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.builder.system.SystemMessageStrategy;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.SystemMessage;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.exceptions.MessageException;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Component
public class FriendAnswerMessage extends SystemMessageStrategy {

    private static final Logger logger = LoggerFactory.getLogger(FriendAnswerMessage.class);


    @Override
    @Transactional
    public Message createMessage(String messageTemplate, String[] params, User publisher, Chat chat) {
        logger.debug("-> createMessage chatId : " + chat.getChatId());

        SystemMessage systemMessage = (SystemMessage) chat.getMessages().iterator().next();

        if (Objects.nonNull(systemMessage) && systemMessage.getSystemMessageStatus() == SystemMessageStatus.INVITE) {
            systemMessage.setStatusSystem();
        } else {
            throw new MessageException("SystemMessage doesn't exist ");
        }

        return messageSourceBuilder.createMessage(messageTemplate, params, publisher, chat, SystemMessageStatus.SYSTEM);
    }

}
