package com.social.network.redis.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Yadykin Andrii Nov 29, 2016
 *
 */
@FeignClient("message-service")
public interface MessageClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/MessageService/chat/message")
    long saveMessage(@RequestBody MessageDto messageDto);
}
