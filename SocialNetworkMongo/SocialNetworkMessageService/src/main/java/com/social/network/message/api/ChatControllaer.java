package com.social.network.message.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.message.service.MongoChatService;

/**
 * Created by Yadykin Andrii Nov 28, 2016
 *
 */

@RestController
public class ChatControllaer {
    private final static Logger logger = LoggerFactory.getLogger(ChatControllaer.class);
    
    @Autowired
    private MongoChatService mongoChatService;

    @RequestMapping("/chat")
    public String byNumber() {

        mongoChatService.saveChat(1);
        return "Ok";
    }
}
