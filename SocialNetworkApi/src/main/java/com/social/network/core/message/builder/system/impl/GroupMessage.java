package com.social.network.core.message.builder.system.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.builder.system.SystemMessageStrategy;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Service
public class GroupMessage extends SystemMessageStrategy {

    @Override
    @Transactional
    public Message createMessage(String messageTemplate, String[] params, User publisher, Chat chat) {
        return messageSourceBuilder.createMessage(messageTemplate, params, publisher, chat, SystemMessageStatus.SYSTEM);
    }

}
