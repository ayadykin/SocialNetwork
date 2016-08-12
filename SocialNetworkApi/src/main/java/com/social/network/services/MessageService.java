package com.social.network.services;

import java.util.Set;

import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.User;
import com.social.network.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

public interface MessageService {

    Message createMessage(String messageText, User publisher, Set<User> subscribers, Chat chat);
    
    Message createSystemMessage(String messageText, User publisher, Set<User> subscribers, Chat chat, SystemMessageStatus systemMessageStatus);

    Message editMessage(long messageId, String newMessage);

    Message deleteMessage(long messageId);

}
