package com.social.network.message.system;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.social.network.message.builder.MessageSourceBuilder;
import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.User;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

public abstract class SystemMessageStrategy {

    @Autowired
    protected MessageSourceBuilder messageSourceBuilder;

    public abstract Message createMessage(String messageTemplate, String[] params, User publisher, Set<User> subscribers, Chat chat);
}
