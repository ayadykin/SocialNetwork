package com.social.network.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.social.network.redis.service.RedisMessageListener;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisMessageListener messageListener;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer mlc = new RedisMessageListenerContainer();
        mlc.setConnectionFactory(redisConnectionFactory);
        mlc.addMessageListener(messageListener, new PatternTopic("*"));
        return mlc;
    }
}
