package com.social.network.core.message.builder.system;

import org.springframework.beans.factory.annotation.Autowired;

import com.social.network.core.message.builder.MessageSourceBuilder;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

public abstract class SystemMessageStrategy {

    @Autowired
    protected MessageSourceBuilder messageSourceBuilder;

    public abstract Message createMessage(String messageTemplate, String[] params, User publisher, Chat chat);
}
