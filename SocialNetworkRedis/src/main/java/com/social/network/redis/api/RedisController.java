package com.social.network.redis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.redis.model.RedisMessage;
import com.social.network.redis.service.RedisService;

/**
 * Created by Yadykin Andrii Nov 28, 2016
 *
 */

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public RedisMessage getChat() {
        return redisService.getMessage();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public RedisMessage sendMessage(@RequestBody RedisMessage redisMessage) {
        redisService.sendMessageToRedis(redisMessage);
        return redisMessage;
    }
}
