package com.social.network.services.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.RecipientDao;
import com.social.network.dto.MessageDto;
import com.social.network.model.Recipient;
import com.social.network.model.User;
import com.social.network.redis.RedisMessageObserver;
import com.social.network.services.RedisService;
import com.social.network.services.TranslateService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private RecipientDao recipientDao;
    @Autowired
    private RedisTemplate<String, MessageDto> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisMessageObserver redisMessageObserver;
    @Autowired
    private TranslateService translateService;

    @Override
    @Transactional
    public MessageDto getMessage() {
        logger.debug("getMessage ");
        long userId = userService.getLoggedUserId();
        BoundListOperations<String, MessageDto> listOps = redisTemplate.boundListOps(Long.valueOf(userId).toString());

        MessageDto messageDto = listOps.leftPop();
        if (messageDto != null) {

            // Translate
            User loggedUser = userService.getLoggedUserEntity();
            if (loggedUser.getProfile().isTranslate()) {
                Locale locale = loggedUser.getProfile().getLocale();
                logger.debug("getMessage translate to language : {}", locale.getLanguage());
                messageDto.setText(translateService.translateString(messageDto.getText(), locale));
            }
            // Change message status read true
            logger.debug(" updateMessageStatus message = {}", messageDto);
            redisMessageObserver.updateMessageStatus(userId, messageDto.getMessageId());

        }
        logger.debug("getMessage userId = {},  listOps.size = {}, messageDto = {}", userId, listOps.size(), messageDto);

        return messageDto;

    }

    @Override
    public boolean sendMessageToRedis(MessageDto message) {
        logger.debug("sendMessagesToRedis :  message = {}", message);        
        for (Recipient recipient : recipientDao.findRecipientsByMessage(message.getMessageId())) {
            String key = Long.valueOf(recipient.getUserId()).toString();
            redisTemplate.boundListOps(key).rightPush(message);
            redisTemplate.convertAndSend(key, message);
        }

        return true;
    }
}
