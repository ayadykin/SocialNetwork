package com.social.network.message.builder;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.social.network.message.Subscribers;
import com.social.network.model.Chat;
import com.social.network.model.Message;
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

    public Message createMessage(String messageTemplate, String[] arg, Subscribers subscribers, Chat chat) {
        logger.debug("createMessage messageTemplate : {}", messageTemplate);
        String messageText = messageSource.getMessage(messageTemplate, arg, Locale.getDefault());
        return messageService.createMessage(messageText, subscribers, chat);
    }

}
