package com.social.network.services.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.FriendsMailing;
import com.social.network.domain.dao.MessageDao;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.exceptions.chat.ChatPermissionException;
import com.social.network.exceptions.chat.ChatRemovedException;
import com.social.network.services.ChatService;
import com.social.network.services.FriendService;
import com.social.network.services.MessageService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 */

@Service
public class ChatServiceImpl implements ChatService {

	private final static Logger logger = LoggerFactory.getLogger(ChatService.class);
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private UserChatDao userChatDao;
	@Autowired
	private UserService userService;
	@Autowired
	private FriendService friendService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private FriendsMailing friendsNotification;

	@Override
	@Transactional(readOnly = true)
	public Set<UserChat> getChatsList() {
		User loggedUser = userService.getLoggedUserEntity();
		logger.debug("->getChatsList for user : {}", loggedUser.getUserId());

		return loggedUser.getUserChat();
	}

	@Override
	@Transactional(readOnly = true)
	public UserChat getChat(long chatId) {
		logger.debug("->getChat chatId : {}", chatId);
		User loggedUser = userService.getLoggedUserEntity();

		UserChat userChat = userChatDao.findByChatAndUser(chatId, loggedUser.getUserId());

		validateChat(userChat);

		return userChat;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Message> getChatMesasges(long chatId, boolean readed, Date filter) {
		logger.debug("getChatMesasges : chatId = {}, filter = {}", chatId, filter);
		User loggedUser = userService.getLoggedUserEntity();

		// Validation
		getChatMesasgesValidate(chatId, loggedUser.getUserId());

		return messageDao.getMessages(chatId, loggedUser, readed, filter);
	}

	@Override
	@Transactional
	public Message sendMessage(String messageText, long chatId) {
		logger.debug("sendMessage messageText : {}, to chatId : {} ", messageText, chatId);
		User loggedUser = userService.getLoggedUserEntity();

		UserChat userChat = userChatDao.findByChatAndUser(chatId, loggedUser.getUserId());

		// Validation
		sendMessageValidate(userChat);

		return messageService.createMessage(messageText, loggedUser, userChat.getChat());
	}

	@Override
	@Transactional
	public void sendPublicMessage(String messageText) {
		logger.debug("sendPublicMessage messageText : {} ", messageText);
		User loggedUser = userService.getLoggedUserEntity();

		friendsNotification.mailing(messageText, loggedUser);
	}

	private void sendMessageValidate(UserChat userChat) {
		validateChat(userChat);
		validateHiddenChat(userChat);
		validateFriend(userChat);
	}

	private void getChatMesasgesValidate(long chatId, long userId) {
		UserChat userChat = userChatDao.findByChatAndUser(chatId, userId);
		validateChat(userChat);
	}

	private void validateFriend(UserChat userChat) {

		Iterator<UserChat> iterator = userChat.getChat().getUserChat().iterator();
		UserChat inviterUser = iterator.next();
		UserChat inviteeUser = iterator.next();
		if (!friendService.isYourFriend(inviterUser.getUserId(), inviteeUser.getUserId())) {
			throw new ChatPermissionException("Friend not accepted your invitation");
		}
	}

	private void validateChat(UserChat userChat) {
		if (Objects.isNull(userChat)) {
			throw new ChatPermissionException("You haven't permisions for this chat");
		}
	}

	private void validateHiddenChat(UserChat userChat) {
		if (userChat.getChat().getHidden()) {
			throw new ChatRemovedException("Chat was removed");
		}
	}
}
