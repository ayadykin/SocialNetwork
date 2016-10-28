package com.social.network.message.service;

import java.util.List;
import java.util.Set;

import com.social.network.message.domain.model.MongoMessage;

/**
 * Created by Yadykin Andrii Oct 13, 2016
 *
 */

public interface MongoChatService {

    void saveChat(long chatId);
    
    void addMessage(long chatId, String text, long publisher, Set<Long> resiients);
    
    List<MongoMessage> getMessages(long chatId);
}

