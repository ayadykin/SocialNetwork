package com.social.network.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.redis.model.RedisMessage;
/**
 * Created by Yadykin Andrii Oct 17, 2016
 *
 */

public class RedisMessageConverter {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisMessageConverter.class);
    
    /*public static RedisMessage convertMongoMessageToMessageDto(MongoMessage message) {
        logger.debug("strart convertMessageToMessageDto :");

        // Get message's owner name
        String ownerName = "";// message.getPublisher().getUserFullName();
        RedisMessage messageDto = new RedisMessage(message.getMessageId(), message.getText(), message.getCreateDate(), ownerName,
                message.getPublisherId());

        // Get invitation message flag
        /*if (message instanceof SystemMessage) {
             messageDto.setMessageInviteStatus(((SystemMessage)
             message).getSystemMessageStatus());
        }*/
        
        // Get hidden message flag
        /*
         * if (message.getHidden().isHidden()) { messageDto.setHidden(true);
         * messageDto.setText("This message has been removed."); }
         

        return messageDto;
    }*/

    public static RedisMessage createRedisMessage(String messageText, long chatId, String publisherNamme) {
        logger.debug("strart convertMessageToMessageDto :");

        // Get message's owner name
        //String ownerName = message.getPublisher().getUserFullName();
        RedisMessage messageDto = new RedisMessage(messageText, chatId, publisherNamme);

        
        /*// Get invitation message flag
        if (message instanceof SystemMessage) {
            messageDto.setMessageInviteStatus(((SystemMessage) message).getSystemMessageStatus());
        }
        // Get hidden message flag
        if (message.getHidden().isHidden()) {
            messageDto.setHidden(true);
            messageDto.setText("This message has been removed.");
        }*/

        return messageDto;
    }
}
