package com.social.network.rest.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import com.social.network.domain.model.UserChat;
import com.social.network.message.domain.model.MongoMessage;
import com.social.network.redis.model.RedisMessage;
import com.social.network.redis.service.RedisService;
import com.social.network.redis.util.RedisMessageConverter;
import com.social.network.rest.dto.chat.ChatDto;
import com.social.network.rest.utils.EntityToDtoMapper;
import com.social.network.services.ChatService;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Service
public class ChatServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceFacade.class);

    @Autowired
    private ChatService chatService;

    @Transactional(value = "hibernateTx", readOnly = true)
    public List<ChatDto> getChatsList() {

        // Fill ChatDto list
        List<ChatDto> chatsList = new ArrayList<>();
        for (UserChat chat : chatService.getChatsList()) {
            chatsList.add(EntityToDtoMapper.convertChatToChatDto(chat));
        }
        return chatsList;
    }

    public ChatDto getChat(long chatId) {
        return EntityToDtoMapper.convertChatToChatDto(chatService.getChat(chatId));
    }
    
    public boolean sendMessage(String messageText, long chatId, boolean publicMessage) {

        if (publicMessage) {
            chatService.sendPublicMessage(messageText);
        } else {
            chatService.sendMessage(messageText, chatId);
            //redisService.sendMessageToRedis(messageText, chatId, "publisherNamme");
        }

        return true;
    }
    
    //@Transactional(value = "hibernateTx", readOnly = true)
    public List<RedisMessage> getChatMesasges(long chatId) {
        return getChatMesasges(chatId, null);
    }
    
    //@Transactional(value = "hibernateTx", readOnly = true)
    public List<RedisMessage> getChatMesasges(long chatId, Date filter) {

        List<MongoMessage> messagesList = chatService.getChatMesasges(chatId, true, filter);
        // Fill MessageDto list
        List<RedisMessage> messages = new ArrayList<>();
        for (MongoMessage message : messagesList) {
            
            //RedisMessage redisMessageModel = RedisMessageConverter.convertMongoMessageToMessageDto(message);
           // messages.add(redisMessageModel);
        }
        return messages;
    }
    
    public DeferredResult<RedisMessage> getRedisMessage() {
        final DeferredResult<RedisMessage> deferredResult = new DeferredResult<RedisMessage>(null, "");

        RedisMessage redisMessageModel = chatService.getRedisMessage();

        if (Objects.nonNull(redisMessageModel)) {
            deferredResult.setResult(redisMessageModel);
        }

        return deferredResult;
    }

    
    public boolean editMessage(long messageId, String newMessage) {
        //Message message = messageService.editMessage(messageId, newMessage);
        return true;//sendMessageToRedis(message);
    }

    
    public boolean deleteMessage(long messageId) {
        //Message message = messageService.deleteMessage(messageId);
        return true;//sendMessageToRedis(message);
    }

   /* private boolean sendMessageToRedis(Message message) {
        return redisService.sendMessageToRedis(message);
    }  */
}
