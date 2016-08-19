package com.social.network.core.message.builder;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.services.MessageService;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Component
public class MessageSourceBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MessageSourceBuilder.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageService messageService;

    /**
     * Create message from property file template
     * 
     * @param messageTemplate
     * @param arg
     * @param publisher
     * @param chat
     * @param systemMessageStatus
     * @return
     */
    public Message createMessage(String messageTemplate, String[] arg, User publisher, Chat chat,
            SystemMessageStatus systemMessageStatus) {
        logger.debug("createMessage messageTemplate : {}", messageTemplate);
        String messageText = messageSource.getMessage(messageTemplate, arg, Locale.getDefault());
        return messageService.createSystemMessage(messageText, publisher, chat, systemMessageStatus);
    }

}
