package com.social.network.message.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.network.message.domain.model.Chat;
import com.social.network.message.domain.model.MongoMessage;
import com.social.network.message.domain.model.Recipient;
import com.social.network.message.domain.repository.ChatRepository;
import com.social.network.message.domain.repository.MessageRepository;
import com.social.network.message.exceptions.ChatException;
import com.social.network.message.service.CounterService;
import com.social.network.message.service.MongoChatService;

/**
 * Created by Yadykin Andrii Oct 13, 2016
 *
 */

@Slf4j
@Service
public class MongoChatServiceImpl implements MongoChatService {

    private static final String MESSAGE_ID_SEQUENCE_NAME = "message_id";

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private CounterService counterService;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Chat saveChat(long chatId) {
        log.debug(" saveChat chatId : {}", chatId);
        return chatRepository.save(new Chat(chatId));
    }

    @Override
    public MongoMessage addMessage(long chatId, String text, long publisher, Set<Long> resipientsId) {
        log.debug(" addMessage chatId : {}, text : {}, publisher : {}, resipientsId : {}", chatId, text, publisher, resipientsId);
        // Create recipients
        Set<Recipient> recipientsList = new HashSet<>();
        for (long userId : resipientsId) {
            recipientsList.add(new Recipient(userId));
        }

        // Create message
        long meaageId = counterService.getNextSequence(MESSAGE_ID_SEQUENCE_NAME);
        MongoMessage message = new MongoMessage(meaageId, text, publisher, recipientsList);

        // Add message to chat
        Chat chat = chatRepository.findOne(chatId);
        
        if(Objects.isNull(chat)){
            throw new ChatException("Chat not exist");
        }
        chat.addMessage(message);
        chatRepository.save(chat);
        return message;
    }

    @Override
    public List<MongoMessage> getMessages(long chatId) {
        return chatRepository.findOne(chatId).getMessages();
    }

}
