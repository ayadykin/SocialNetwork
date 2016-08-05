package com.social.network.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.social.network.redis.RedisMessageListener;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */
@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.pass}")
    private String redisPassword;
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
            JedisConnectionFactory cf = new JedisConnectionFactory();
            cf.setHostName(redisHost);
            cf.setPort(redisPort);
            cf.setPassword(redisPassword);
            return cf;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
            RedisMessageListenerContainer mlc = new RedisMessageListenerContainer();
            mlc.setConnectionFactory(redisConnectionFactory());
            //mlc.addMessageListener(redisMessageListener , new PatternTopic("chat"));
            return mlc;
    }
}

