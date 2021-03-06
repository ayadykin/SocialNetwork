package com.social.network.redis.service;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.social.network.redis.client.MessageClient;
import com.social.network.redis.client.MessageDto;
import com.social.network.redis.model.RedisMessage;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */

@Slf4j
@Component
public class RedisMessageListener implements MessageListener {
 
    @Autowired
    private MessageClient messageClient;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String chanel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String patterns = new String(pattern, StandardCharsets.UTF_8);
        log.debug(" onMessage : chanel = {}, patterns = {}", chanel, patterns);
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBody());
        try {
            ObjectInputStream is = new ObjectInputStream(in);
            RedisMessage redisMessage = (RedisMessage) is.readObject();
            log.debug(" onMessage redisMessage : {}", redisMessage);
            
            MessageDto messageDto = new MessageDto();
            messageDto.setText(redisMessage.getText());
            messageDto.setChatId(redisMessage.getChatId());
            messageDto.setResipientsId(redisMessage.getRecipientsList());
            
            long meggaseId = messageClient.saveMessage(messageDto);
            log.debug(" onMessage saveMessage meggaseId : {}", meggaseId);
            //mongoChatService.addMessage(redisMessage.getChatId(), redisMessage.getText(), redisMessage.getOwnerId(),
            //        new HashSet<>(Arrays.asList(1l, 3l)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
