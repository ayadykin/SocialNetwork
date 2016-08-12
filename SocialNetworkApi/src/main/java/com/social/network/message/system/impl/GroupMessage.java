package com.social.network.message.system.impl;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.message.system.SystemMessageStrategy;
import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.User;
import com.social.network.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Service
public class GroupMessage extends SystemMessageStrategy {

    @Override
    @Transactional
    public Message createMessage(String messageTemplate, String[] params, User publisher, Set<User> subscribers, Chat chat) {
        return messageSourceBuilder.createMessage(messageTemplate, params, publisher, subscribers, chat, SystemMessageStatus.SYSTEM);
    }

}
