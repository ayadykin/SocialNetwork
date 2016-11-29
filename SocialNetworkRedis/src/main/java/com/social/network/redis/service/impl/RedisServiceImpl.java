package com.social.network.redis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.social.network.redis.client.MessageClient;
import com.social.network.redis.model.RedisMessage;
import com.social.network.redis.service.RedisService;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private RedisTemplate<String, RedisMessage> redisTemplate;
    @Autowired
    private MessageClient client;
    /*
     * @Autowired private UserService userService;
     * 
     * @Autowired private TranslateService translateService;
     */

    @Override
    // @Transactional(value = "hibernateTx")
    public RedisMessage getMessage() {
        logger.debug("getMessage ");
        long userId = 0;// userService.getLoggedUserId();
        BoundListOperations<String, RedisMessage> listOps = redisTemplate.boundListOps(Long.valueOf(userId).toString());

        // User loggedUser = userService.getLoggedUserEntity();
        RedisMessage messageDto = null;
        try {
            messageDto = (RedisMessage) listOps.leftPop();
            if (messageDto != null) {
                /*
                 * if (loggedUser.getProfile().isTranslate()) { logger.debug(
                 * "get translated message");
                 * messageDto.setText(translateService.translateString(
                 * messageDto.getText(), loggedUser.getLocale())); }
                 */
                // Change message status read true
                logger.debug(" updateMessageStatus message = {}", messageDto);
                // redisMessageObserver.setMessageToReaded(userId,
                // messageDto.getMessageId());
                logger.debug("getMessage userId = {},  listOps.size = {}, messageDto = {}", userId, listOps.size(), messageDto);
            }
        } catch (Exception e) {
            logger.error(" getMessage error : {}", e.getMessage());
        }
        return messageDto;
    }

    @Override
    // @Transactional(value = "hibernateTx", readOnly = true)
    public boolean sendMessageToRedis(RedisMessage redisMessage) {
        logger.debug("sendMessagesToRedis :  redisMessage = {}", redisMessage);
        // RedisMessage redisMessageModel =
        // RedisMessageConverter.createRedisMessage(message, chatId,
        // publisherNamme);
        // message = messageDao.merge(message);
        try {
            // for (Chat chat : message.getChat()) {
            // redisMessageModel.setChat(chat.getChatId());
            // for (Recipient recipient : message.getRecipient()) {
            String key = String.valueOf(redisMessage.getChatId());
            redisTemplate.boundListOps(key).rightPush(redisMessage);
            redisTemplate.convertAndSend(key, redisMessage);
            // }
            // }
        } catch (Exception e) {
            logger.error("sendMessageToRedis {}", e);
            return false;
        }

        return true;
    }

}
