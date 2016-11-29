package com.social.network.message.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.message.domain.model.MongoMessage;
import com.social.network.message.dto.MessageDto;
import com.social.network.message.service.MongoChatService;

/**
 * Created by Yadykin Andrii Nov 28, 2016
 *
 */

@RestController
@RequestMapping("/chat")
public class ChatController {

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

    @PostMapping("/message")
    public void addMessage(@RequestBody MessageDto messageDto) {
        mongoChatService.addMessage(messageDto.getChatId(), messageDto.getText(), messageDto.getPublisher(), messageDto.getResipientsId());
    }
}
