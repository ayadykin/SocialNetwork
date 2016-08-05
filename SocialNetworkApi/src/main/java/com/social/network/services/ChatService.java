package com.social.network.services;

import java.util.List;
import java.util.Set;

import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.enums.Period;

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
    Set<Chat> getChatsList();

    /**
     * Get filtered by period messages for chat
     * 
     * @param chatId
     * @param readed
     * @param period
     * 
     * @return list of messages
     */
    Set<Message> getChatMesasges(long chatId, boolean readed, Period period);

    /**
     * Send message to chat
     * 
     * @param messageText
     * @param chatId
     * 
     * @return message
     */
    Message sendMessage(String messageText, long chatId);
    
    /**
     * Get chat name (Group or user name).
     * 
     * @param chat
     * @param userId
     * @return
     */
    String getChatName(Chat chat, long userId);

}
