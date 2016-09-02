package com.social.network.rest.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import com.social.network.domain.model.Message;
import com.social.network.domain.model.UserChat;
import com.social.network.rest.dto.MessageDto;
import com.social.network.rest.dto.chat.ChatDto;
import com.social.network.rest.utils.EntityToDtoMapper;
import com.social.network.services.ChatService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Service
public class ChatServiceFacade {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;

    @Transactional
    public List<ChatDto> getChatsList() {
        long userId = userService.getLoggedUserId();

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
    public List<MessageDto> getChatMesasges(long chatId) {
        return getChatMesasges(chatId, null);
    }

    @Transactional
    public List<MessageDto> getChatMesasges(long chatId, Date filter) {

        List<Message> messagesList = chatService.getChatMesasges(chatId, true, filter);
        // Fill MessageDto list
        List<MessageDto> messages = new ArrayList<>();
        for (Message message : messagesList) {
            MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message);
            messages.add(messageDto);
        }
        return messages;
    }
    
    @Transactional
    public DeferredResult<MessageDto> getRedisMessage() {
        final DeferredResult<MessageDto> deferredResult = new DeferredResult<MessageDto>(null, "");

        /*MessageDto message = redisService.getMessage();
        if (Objects.nonNull(message)) {
            deferredResult.setResult(message);
        }*/

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
