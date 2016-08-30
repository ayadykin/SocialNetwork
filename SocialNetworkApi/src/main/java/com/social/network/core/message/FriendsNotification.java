package com.social.network.core.message;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.dto.MessageDto;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.utils.EntityToDtoMapper;

/**
 * Created by Yadykin Andrii Aug 30, 2016
 *
 */

@Component
public class FriendsNotification {
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;

    @Transactional
    public void notificate(String messageText, User publisher) {

        Set<Chat> chats = publisher.getFriends().stream().map(f -> f.getChat()).collect(Collectors.toSet());

        Message message = messageService.createMialing(messageText, publisher, chats);

        for (Chat publicChat : chats) {
            MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message);
            messageDto.setChatId(publicChat.getChatId());
            redisService.sendMessageToRedis(messageDto);
        }
    }
}
