package com.social.network.core.message.builder;

import org.springframework.stereotype.Component;

import com.social.network.core.message.builder.system.SystemMessageStrategy;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Component
public class MessageBuilder {

    private SystemMessageStrategy systemMessageStrategy;

    /**
     * Set message builder strategy
     * 
     * @param systemMessageBuilder
     * @return
     */
    public MessageBuilder setMessageBuilder(SystemMessageStrategy systemMessageBuilder) {
        this.systemMessageStrategy = systemMessageBuilder;
        return this;
    }

    /**
     * Create message with one param
     * 
     * @param messageTemplate
     * @param publisher
     * @param chat
     * @return
     */
    public Message createOneParamMessage(String messageTemplate, User publisher, Chat chat) {
        String[] arg = new String[] { publisher.getUserFullName() };
        return systemMessageStrategy.createMessage(messageTemplate, arg, publisher, chat);
    }

    public Message createTwoParamsMessage(String messageTemplate, User user, User publisher, Chat chat) {
        String[] arg = new String[] { publisher.getUserFullName(), user.getUserFullName() };
        return systemMessageStrategy.createMessage(messageTemplate, arg, publisher, chat);
    }
}
