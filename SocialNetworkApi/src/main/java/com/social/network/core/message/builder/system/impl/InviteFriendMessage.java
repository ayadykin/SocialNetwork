package com.social.network.core.message.builder.system.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.builder.system.SystemMessageStrategy;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii Jul 26, 2016
 *
 */

@Service
public class InviteFriendMessage extends SystemMessageStrategy {

    
    @Override
    @Transactional
    public Message createMessage(String messageText, User publisher, Chat chat) {
        return messageSourceBuilder.createMessage(messageText, publisher, chat, SystemMessageStatus.INVITE);
    }
}
