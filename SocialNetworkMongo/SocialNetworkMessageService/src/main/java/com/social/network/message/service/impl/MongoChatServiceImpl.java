package com.social.network.message.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.network.message.domain.model.Chat;
import com.social.network.message.domain.model.Message;
import com.social.network.message.domain.model.Recipient;
import com.social.network.message.domain.repository.ChatRepository;
import com.social.network.message.domain.repository.MessageRepository;
import com.social.network.message.service.CounterService;
import com.social.network.message.service.MongoChatService;

/**
 * Created by Yadykin Andrii Oct 13, 2016
 *
 */

@Service
public class MongoChatServiceImpl implements MongoChatService {

    private final static Logger logger = LoggerFactory.getLogger(MongoChatService.class);

    private static final String MESSAGE_ID_SEQUENCE_NAME = "message_id";

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private CounterService counterService;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveChat(long chatId) {
        logger.debug(" saveChat chatId : {}", chatId);
        chatRepository.save(new Chat(chatId));
    }

    @Override
    public void addMessage(long chatId, String text, long publisher, Set<Long> resipientsId) {
        logger.debug(" addMessage chatId : {}, text : {}, publisher : {}, resipientsId : {}", chatId, text, publisher, resipientsId);
        // Create recipients
        Set<Recipient> recipientsList = new HashSet<>();
        for (long userId : resipientsId) {
            recipientsList.add(new Recipient(userId));
        }

        // Create message
        long meaageId = counterService.getNextSequence(MESSAGE_ID_SEQUENCE_NAME);
        Message message = new Message(meaageId, text, publisher, recipientsList);

        // Add message to chat
        Chat chat = chatRepository.findOne(chatId);
        chat.addMessage(message);
        chatRepository.save(chat);
    }

    @Override
    public List<Message> getMessages(long chatId) {
        return chatRepository.findOne(chatId).getMessages();
    }

}
