package com.social.network.services;

import java.util.List;

import com.social.network.message.Subscribers;
import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.User;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

public interface MessageService {

    Message createMessage(String messageText, Subscribers subscribers, Chat chat);

    Message editMessage(long messageId, String newMessage);

    Message deleteMessage(long messageId);

}
