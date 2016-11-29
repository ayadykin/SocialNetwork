package com.social.network.redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.social.network.redis.model.RedisMessage;
import com.social.network.redis.service.RedisService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Yadykin Andrii Nov 28, 2016
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = { MessageServer.class }, loader =
// AnnotationConfigContextLoader.class)
@SpringApplicationConfiguration(classes = RedisServer.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, RedisMessage> redisTemplate;
    
    @Autowired
    private RedisService redisService;
    
    @Before
    public void init() {
        assertNotNull(redisTemplate);
    }
    
    @Test
    public void test() {
        RedisMessage message = new RedisMessage();
        message.setText("message");
        assertTrue(redisService.sendMessageToRedis(message));
        
        RedisMessage redisMessage = redisService.getMessage();
        
        assertEquals("message", redisMessage.getText());
    }
}
