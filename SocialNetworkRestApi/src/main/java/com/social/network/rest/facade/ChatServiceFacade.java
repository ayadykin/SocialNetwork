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

import com.social.network.domain.model.Message;
import com.social.network.domain.model.UserChat;
import com.social.network.redis.RedisMessageModel;
import com.social.network.rest.dto.chat.ChatDto;
import com.social.network.rest.utils.EntityToDtoMapper;
import com.social.network.services.ChatService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.services.impl.RedisServiceImpl;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Service
public class ChatServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceFacade.class);

    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;

    @Transactional
    public List<ChatDto> getChatsList() {

        // Fill ChatDto list
        List<ChatDto> chatsList = new ArrayList<>();
        for (UserChat chat : chatService.getChatsList()) {
            chatsList.add(EntityToDtoMapper.convertChatToChatDto(chat));
        }
        return chatsList;
    }

    @Transactional
    public ChatDto getChat(long chatId) {
        return EntityToDtoMapper.convertChatToChatDto(chatService.getChat(chatId));
    }

    /**
     * Add message to DB and send to redis
     * 
     * @param messageText
     * @param chatId
     * @param publicMessage
     * @return
     */
    
    @Transactional
    public boolean sendMessage(String messageText, long chatId, boolean publicMessage) {

        if (publicMessage) {
            chatService.sendPublicMessage(messageText);
        } else {
            Message message = chatService.sendMessage(messageText, chatId);
            sendMessageToRedis(message);
        }

        return true;
    }

    @Transactional
    public List<RedisMessageModel> getChatMesasges(long chatId) {
        return getChatMesasges(chatId, null);
    }

    /**
     * Get messages from DB
     * 
     * @param chatId
     * @param filter
     * @return
     */
    @Transactional
    public List<RedisMessageModel> getChatMesasges(long chatId, Date filter) {

        List<Message> messagesList = chatService.getChatMesasges(chatId, true, filter);
        // Fill MessageDto list
        List<RedisMessageModel> messages = new ArrayList<>();
        for (Message message : messagesList) {
            RedisMessageModel redisMessageModel = RedisServiceImpl.convertMessageToMessageModel(message);
            messages.add(redisMessageModel);

        }
        return messages;
    }

    /**
     * Get message from redis
     * 
     * @return
     */
    @Transactional
    public DeferredResult<RedisMessageModel> getRedisMessage() {
        final DeferredResult<RedisMessageModel> deferredResult = new DeferredResult<RedisMessageModel>(null, "");

        RedisMessageModel redisMessageModel = redisService.getMessage();

        if (Objects.nonNull(redisMessageModel)) {
            deferredResult.setResult(redisMessageModel);
        }

        return deferredResult;
    }

    @Transactional
    public boolean editMessage(long messageId, String newMessage) {
        Message message = messageService.editMessage(messageId, newMessage);
        return sendMessageToRedis(message);
    }

    @Transactional
    public boolean deleteMessage(long messageId) {
        Message message = messageService.deleteMessage(messageId);
        return sendMessageToRedis(message);
    }

    private boolean sendMessageToRedis(Message message) {
        return redisService.sendMessageToRedis(message);
    }  
}
