package com.social.network.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.FriendsNotification;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.Period;
import com.social.network.dto.MessageDto;
import com.social.network.dto.chat.ChatDto;
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
    @Autowired
    private FriendsNotification friendsNotification;

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

        Message message = chatService.sendMessage(messageText, chatId);
        
        if (publicMessage) {
            friendsNotification.notificate(messageText, message.getPublisher());
        }
        
        return sendMessageToRedis(message);
    }

    @Transactional
    public List<MessageDto> getChatMesasges(long chatId, Period filter) {

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
        MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message);
        return redisService.sendMessageToRedis(messageDto);
    }
}
