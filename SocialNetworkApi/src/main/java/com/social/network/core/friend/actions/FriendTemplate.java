package com.social.network.core.friend.actions;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.text.MessageTextBuilder;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.SystemMessage;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.dto.MessageDto;
import com.social.network.exceptions.MessageException;
import com.social.network.services.FriendService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.utils.EntityToDtoMapper;

public abstract class FriendTemplate {

	@Autowired
	protected FriendService friendService;
	@Autowired
	private MessageTextBuilder messageTextBuilder;
	@Autowired
	protected MessageService messageService;
	@Autowired
	private RedisService redisService;

	final public boolean friendAction(String template, User loggedUser, Chat chat) {
				
		String messageText = createOneParamMessage(template, loggedUser.getUserFullName());
		Message message = createMessage(messageText, loggedUser, chat);
		return sendMessageToRedis(message);
	}
	
	final public boolean groupAction(String template, User loggedUser, Chat chat) {
		
		String messageText = createOneParamMessage(template, loggedUser.getUserFullName());
		Message message = createMessage(messageText, loggedUser, chat);
		return sendMessageToRedis(message);
	}

	public String createOneParamMessage(String template, String param) {
		return messageTextBuilder.createOneParamMessage(template, param);
	}

	public abstract Message createMessage(String messageText, User publisher, Chat chat);

	public boolean sendMessageToRedis(Message message) {
		MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message);
		return redisService.sendMessageToRedis(messageDto);
	}

	@Transactional
	public Message createSystemMessage(String messageTemplate, User publisher, Chat chat) {

		SystemMessage systemMessage = (SystemMessage) chat.getMessages().iterator().next();

		if (Objects.nonNull(systemMessage) && systemMessage.getSystemMessageStatus() == SystemMessageStatus.INVITE) {
			systemMessage.setStatusSystem();
		} else {
			throw new MessageException("SystemMessage doesn't exist ");
		}

		return messageService.createSystemMessage(messageTemplate, publisher, chat, SystemMessageStatus.SYSTEM);
	}
}
