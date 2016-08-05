package com.social.network.message.system.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.message.Subscribers;
import com.social.network.message.system.SystemMessageStrategy;
import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.SystemMessage;
import com.social.network.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii Jul 26, 2016
 *
 */

@Service
public class InviteFriendMessage extends SystemMessageStrategy {

    @Override
    @Transactional
    public Message createMessage(String messageTemplate, String[] params, Subscribers subscribers, Chat chat) {
        Message message = messageSourceBuilder.createMessage(messageTemplate, params, subscribers, chat);
        message.setSystemMessage(new SystemMessage(message, SystemMessageStatus.INVITE));
        return message;

    }
}
