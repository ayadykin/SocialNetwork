package com.social.network.message.api;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.message.domain.model.Chat;
import com.social.network.message.domain.model.MongoMessage;
import com.social.network.message.dto.MessageDto;
import com.social.network.message.service.MongoChatService;

/**
 * Created by Yadykin Andrii Nov 28, 2016
 *
 */

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private MongoChatService mongoChatService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MongoMessage> getChat(@PathVariable("id") long chatId) {
        log.debug("getChat chatId : {}", chatId);
        List<MongoMessage> messages = mongoChatService.getMessages(chatId);
        log.info("getChat messages : {}", messages);
        return messages;
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Chat saveChat(@PathVariable("id") long chatId) {
        return mongoChatService.saveChat(chatId);
    }

    @PutMapping(value = "/message",produces = MediaType.APPLICATION_JSON_VALUE)
    public long addMessage(@RequestBody MessageDto messageDto) {
        log.info("addMessage messageDto : {}", messageDto);
        
        
        MongoMessage mongoMessage =  mongoChatService.addMessage(messageDto.getChatId(), messageDto.getText(), messageDto.getPublisher(),
                messageDto.getResipientsId());
        return mongoMessage.getMessageId();
    }
}
