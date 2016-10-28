package com.social.network.core.friend.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.social.network.services.MessageService;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Component
public class FriendAnswerMessage {

    private static final Logger logger = LoggerFactory.getLogger(FriendAnswerMessage.class);

    @Autowired
    private MessageService messageService;
    
    /*public Message createSystemMessage(String messageTemplate,  User publisher, Chat chat) {
        logger.debug("-> createMessage chatId : " + chat.getChatId());

        //TODO Refactor find system message by status
        SystemMessage systemMessage = (SystemMessage) chat.getMessages().iterator().next();

        if (Objects.nonNull(systemMessage) && systemMessage.getSystemMessageStatus() == SystemMessageStatus.INVITE) {
            systemMessage.setStatusSystem();
        } else {
            throw new MessageException("SystemMessage doesn't exist ");
        }

        return messageService.createSystemMessage(messageTemplate, publisher, chat, SystemMessageStatus.SYSTEM);
    }*/

}
