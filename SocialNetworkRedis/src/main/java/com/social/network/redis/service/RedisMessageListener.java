package com.social.network.redis.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.social.network.message.service.MongoChatService;
import com.social.network.redis.model.RedisMessage;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */

@Component
public class RedisMessageListener implements MessageListener {

    private final static Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    private MongoChatService mongoChatService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String chanel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String messageTest = new String(message.getBody(), StandardCharsets.UTF_8);
        String patterns = new String(pattern, StandardCharsets.UTF_8);
        logger.debug(" onMessage : chanel = {}, patterns = {}, message = {}", chanel, patterns, messageTest);
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBody());
        try {
            ObjectInputStream is = new ObjectInputStream(in);
            RedisMessage redisMessage = (RedisMessage) is.readObject();
            logger.debug(" onMessage redisMessage : {}", redisMessage);
            mongoChatService.addMessage(redisMessage.getChatId(), redisMessage.getText(), redisMessage.getOwnerId(),
                    new HashSet<>(Arrays.asList(1l, 3l)));
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}