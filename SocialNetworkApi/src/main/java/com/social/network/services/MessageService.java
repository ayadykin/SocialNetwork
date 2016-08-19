package com.social.network.services;

import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

public interface MessageService {

    Message createMessage(String messageText, User publisher, Chat chat);
    
    Message createSystemMessage(String messageText, User publisher, Chat chat, SystemMessageStatus systemMessageStatus);

    Message editMessage(long messageId, String newMessage);

    Message deleteMessage(long messageId);

}
