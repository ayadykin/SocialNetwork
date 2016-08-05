package com.social.network.message.builder;

import org.springframework.stereotype.Component;

import com.social.network.message.Subscribers;
import com.social.network.message.system.SystemMessageStrategy;
import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.User;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Component
public class MessageBuilder {

    private SystemMessageStrategy systemMessageStrategy;

    public MessageBuilder setMessageBuilder(SystemMessageStrategy systemMessageBuilder) {
        this.systemMessageStrategy = systemMessageBuilder;
        return this;
    }

    public Message createOneParamMessage(String messageTemplate, Subscribers subscribers, Chat chat) {
        String[] arg = new String[] { subscribers.getPublisher().getUserFullName() };
        return systemMessageStrategy.createMessage(messageTemplate, arg, subscribers, chat);
    }

    public Message createTwoParamsMessage(String messageTemplate, User user, Subscribers subscribers, Chat chat) {
        String[] arg = new String[] { subscribers.getPublisher().getUserFullName(), user.getUserFullName() };
        return systemMessageStrategy.createMessage(messageTemplate, arg, subscribers, chat);
    }
}
