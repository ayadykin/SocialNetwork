package com.social.network.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.social.network.domain.model.Message;
import com.social.network.domain.model.UserChat;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 * Service for user's chats
 */

public interface ChatService {

    /**
     * Get list of chats
     * 
     * @return list of chats
     */
    Set<UserChat> getChatsList();
    
    /**
     * @param chatId
     * @return
     */
    UserChat getChat(long chatId);

    /**
     * Get filtered by period messages for chat
     * 
     * @param chatId
     * @param readed
     * @param period
     * 
     * @return list of messages
     */
    List<Message> getChatMesasges(long chatId, boolean readed, Date filter);

    /**
     * Send message to chat
     * 
     * @param messageText
     * @param chatId
     * 
     * @return message
     */
    Message sendMessage(String messageText, long chatId);
    
    void sendPublicMessage(String messageText);
}
