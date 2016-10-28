package com.social.network.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.social.network.domain.model.Chat;
import com.social.network.domain.model.UserChat;
import com.social.network.message.domain.model.MongoMessage;
import com.social.network.redis.model.RedisMessage;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 * Service for user's chats
 */

public interface ChatService extends GenericService<Chat> {

    Set<UserChat> getChatsList();
    
    UserChat getChat(long chatId);

    List<MongoMessage> getChatMesasges(long chatId, boolean readed, Date filter);
    
    RedisMessage getRedisMessage();

    void sendMessage(String messageText, long chatId);
    
    void sendPublicMessage(String messageText);
}
