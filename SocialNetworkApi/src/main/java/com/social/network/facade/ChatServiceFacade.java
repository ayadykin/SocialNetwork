package com.social.network.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dto.ChatDto;
import com.social.network.dto.MessageDto;
import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.enums.Period;
import com.social.network.services.ChatService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.services.UserService;
import com.social.network.utils.EntityToDtoMapper;

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

        List<ChatDto> chatsList = new ArrayList<>();

        // Fill ChatDto list
        for (Chat chat : chatService.getChatsList()) {
            String chatName = chatService.getChatName(chat, userId);
            chatsList.add(EntityToDtoMapper.convertChatToChatDto(chat, chatName));
        }
        return chatsList;
    }

    @Transactional
    public boolean sendMessage(String messageText, long chatId) {
        long userId = userService.getLoggedUserId();

        Message message = chatService.sendMessage(messageText, chatId);

        return sendMessageToRedis(message, userId);
    }

    @Transactional
    public List<MessageDto> getChatMesasges(long chatId, Period filter) {

        long userId = userService.getLoggedUserId();
        Set<Message> messagesList = chatService.getChatMesasges(chatId, true, filter);
        // Fill MessageDto list
        List<MessageDto> messages = new ArrayList<>();
        for (Message message : messagesList) {
            MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message, userId);
            messages.add(messageDto);
        }
        return messages;
    }

    @Transactional
    public boolean editMessage(long messageId, String newMessage) {
        long userId = userService.getLoggedUserId();

        Message message = messageService.editMessage(messageId, newMessage);

        return sendMessageToRedis(message, userId);
    }

    @Transactional
    public boolean deleteMessage(long messageId) {
        long userId = userService.getLoggedUserId();

        Message message = messageService.deleteMessage(messageId);

        return sendMessageToRedis(message, userId);
    }

    private boolean sendMessageToRedis(Message message, long userId) {
        MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message, userId);
        return redisService.sendMessageToRedis(messageDto);
    }
}
