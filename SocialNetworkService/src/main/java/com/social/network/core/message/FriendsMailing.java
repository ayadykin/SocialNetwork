package com.social.network.core.message;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.social.network.domain.model.Chat;
import com.social.network.domain.model.User;
import com.social.network.redis.service.RedisService;
import com.social.network.services.MessageService;

/**
 * Created by Yadykin Andrii Aug 30, 2016
 *
 */

@Component
public class FriendsMailing {
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;

    public void mailing(String messageText, User publisher) {

        Set<Chat> chats = publisher.getFriends().stream().map(f -> f.getChat()).collect(Collectors.toSet());

        //Message message = messageService.createMialing(messageText, publisher, chats);

        //redisService.sendMessageToRedis(message);

    }
}
