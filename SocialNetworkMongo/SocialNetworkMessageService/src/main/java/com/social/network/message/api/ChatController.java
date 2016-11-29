package com.social.network.message.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.message.domain.model.MongoMessage;
import com.social.network.message.service.MongoChatService;

/**
 * Created by Yadykin Andrii Nov 28, 2016
 *
 */

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    @Autowired
    private MongoChatService mongoChatService;

    @GetMapping
    public List<MongoMessage> getChat() {

        return mongoChatService.getMessages(10);
    }
    
    @PostMapping
    public String saveChat() {

        mongoChatService.saveChat(1);
        return "Ok";
    }
}
