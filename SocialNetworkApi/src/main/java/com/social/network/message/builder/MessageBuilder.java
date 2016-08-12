package com.social.network.message.builder;

import java.util.Set;

import org.springframework.stereotype.Component;

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

    public Message createOneParamMessage(String messageTemplate, User publisher, Set<User> subscribers, Chat chat) {
        String[] arg = new String[] { publisher.getUserFullName() };
        return systemMessageStrategy.createMessage(messageTemplate, arg, publisher, subscribers, chat);
    }

    public Message createTwoParamsMessage(String messageTemplate, User user, User publisher, Set<User> subscribers, Chat chat) {
        String[] arg = new String[] { publisher.getUserFullName(), user.getUserFullName() };
        return systemMessageStrategy.createMessage(messageTemplate, arg, publisher, subscribers, chat);
    }
}
