package com.social.network.message.system;

import org.springframework.beans.factory.annotation.Autowired;

import com.social.network.message.Subscribers;
import com.social.network.message.builder.MessageSourceBuilder;
import com.social.network.model.Chat;
import com.social.network.model.Message;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

public abstract class SystemMessageStrategy {
   
    @Autowired
    protected MessageSourceBuilder messageSourceBuilder;
    
    public abstract Message createMessage(String messageTemplate, String [] params, Subscribers subscribers, Chat chat);
}
