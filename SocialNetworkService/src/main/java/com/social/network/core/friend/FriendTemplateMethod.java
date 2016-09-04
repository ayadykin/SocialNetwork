package com.social.network.core.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.friend.message.FriendAnswerMessage;
import com.social.network.core.message.text.MessageTextBuilder;
import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.FriendDao;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.services.FriendService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.services.UserService;

public abstract class FriendTemplateMethod {

	@Autowired
	private UserChatDao userChatDao;
	@Autowired
	private ChatDao chatDao;
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private UserService userService;
	@Autowired
	protected FriendService friendService;
	@Autowired
	private MessageTextBuilder messageTextBuilder;
	@Autowired
	protected MessageService messageService;
	@Autowired
	protected FriendAnswerMessage friendAnswerMessage;
	@Autowired
	private RedisService redisService;

	final public Friend friendAction(String template, long userId, FriendStatus friendStatus) {

		User loggedUser = userService.getLoggedUserEntity();

		Friend inviteeFriend = friendDao.findByFriendAndOwner(loggedUser.getUserId(), userId);
		Friend inviterFriend = friendDao.findByFriendAndOwner(userId, loggedUser.getUserId());

		// 1. Validate
		validateStatus(inviteeFriend, inviterFriend, friendStatus);

		// 2. Create friend or update status
		if (FriendStatus.NEW == friendStatus) {
			inviteeFriend = createNewFriend(loggedUser, userId);
		} else {
			updateStatus(inviterFriend, inviteeFriend, friendStatus);
		}

		// 3. Create message text
		String messageText = createMessageText(template, loggedUser.getUserFullName());

		// 4. Create message entity
		Message message = createMessage(messageText, loggedUser, inviteeFriend.getChat());

		// 5. Send to redis
		sendMessageToRedis(message);
		return inviteeFriend;
	}

	// 1
	protected abstract void validateStatus(Friend inviterFriend, Friend inviteeFriend, FriendStatus status);

	// 2
	@Transactional
	private Friend createNewFriend(User loggedUser, long userId) {
		User invitee = userService.getUserById(userId);
		// Create friend
		Chat chat = chatDao.merge(new Chat());

		Friend friend = friendDao.merge(new Friend(chat, FriendStatus.INVITER, loggedUser, invitee));
		friendDao.merge(new Friend(chat, FriendStatus.INVITEE, invitee, loggedUser));

		// Set chat name
		userChatDao.merge(new UserChat(chat, loggedUser, invitee.getUserFullName()));
		userChatDao.merge(new UserChat(chat, invitee, loggedUser.getUserFullName()));
		return friend;
	}
	@Transactional
	private void updateStatus(Friend inviterFriend, Friend inviteeFriend, FriendStatus status) {
		inviterFriend.setFriendStatus(status);
		inviteeFriend.setFriendStatus(status);
	}

	// 3
	private String createMessageText(String template, String param) {
		return messageTextBuilder.createOneParamMessage(template, param);
	}

	// 4
	protected abstract Message createMessage(String messageText, User publisher, Chat chat);

	// 5
	private boolean sendMessageToRedis(Message message) {
		return redisService.sendMessageToRedis(message);
	}
}
