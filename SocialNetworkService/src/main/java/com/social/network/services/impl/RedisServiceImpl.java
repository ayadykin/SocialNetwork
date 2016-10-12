package com.social.network.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.MessageDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.Recipient;
import com.social.network.domain.model.SystemMessage;
import com.social.network.domain.model.User;
import com.social.network.redis.RedisMessageModel;
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
	private RedisTemplate<String, RedisMessageModel> redisTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisMessageObserver redisMessageObserver;
	@Autowired
	private TranslateService translateService;
	@Autowired
	private MessageDao messageDao;

	@Override
	@Transactional(value="hibernateTx")
	public RedisMessageModel getMessage() {
		logger.debug("getMessage ");
		long userId = userService.getLoggedUserId();
		BoundListOperations<String, RedisMessageModel> listOps = redisTemplate
				.boundListOps(Long.valueOf(userId).toString());

		User loggedUser = userService.getLoggedUserEntity();
		RedisMessageModel messageDto = null;
		try {
			messageDto = (RedisMessageModel) listOps.leftPop();
			if (messageDto != null) {
				if (loggedUser.getProfile().isTranslate()) {
					logger.debug("get translated message");
					messageDto.setText(translateService.translateString(messageDto.getText(), loggedUser.getLocale()));
				}
				// Change message status read true
				logger.debug(" updateMessageStatus message = {}", messageDto);
				redisMessageObserver.setMessageToReaded(userId, messageDto.getMessageId());
				logger.debug("getMessage userId = {},  listOps.size = {}, messageDto = {}", userId, listOps.size(),
						messageDto);
			}
		} catch (Exception e) {
			logger.error(" getMessage error : {}", e.getMessage());
		}
		return messageDto;
	}

	@Override
	@Transactional(value="hibernateTx", readOnly = true)
	public boolean sendMessageToRedis(Message message) {
		logger.debug("sendMessagesToRedis :  message = {}", message);
		RedisMessageModel redisMessageModel = RedisServiceImpl.convertMessageToMessageModel(message);
		message = messageDao.merge(message);
		try {
			for (Chat chat : message.getChat()) {
				redisMessageModel.setChat(chat.getChatId());
				for (Recipient recipient : message.getRecipient()) {
					String key = Long.valueOf(recipient.getUserId()).toString();
					redisTemplate.boundListOps(key).rightPush(redisMessageModel);
					redisTemplate.convertAndSend(key, redisMessageModel);
				}
			}
		} catch (Exception e) {
			logger.error("sendMessageToRedis {}", e);
		}

		return true;
	}

	public static RedisMessageModel convertMessageToMessageModel(Message message) {
		logger.debug("strart convertMessageToMessageDto :");

		// Get message's owner name
		String ownerName = message.getPublisher().getUserFullName();
		RedisMessageModel messageDto = new RedisMessageModel(message.getMessageId(), message.getText(),
				message.getCreateDate(), ownerName, message.getPublisherId());

		// Get invitation message flag
		if (message instanceof SystemMessage) {
			messageDto.setMessageInviteStatus(((SystemMessage) message).getSystemMessageStatus());
		}
		// Get hidden message flag
		if (message.getHidden().isHidden()) {
			messageDto.setHidden(true);
			messageDto.setText("This message has been removed.");
		}

		return messageDto;
	}
}