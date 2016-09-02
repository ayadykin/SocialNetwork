package com.social.network.redis;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */

@Component
public class RedisMessageListener implements MessageListener {

    private final static Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String chanel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String messageTest = new String(message.getBody(), StandardCharsets.UTF_8);
        String patterns = new String(pattern, StandardCharsets.UTF_8);
        logger.debug(" onMessage : chanel = {}, patterns = {}, message = {}", chanel, patterns, messageTest);

    }

}
