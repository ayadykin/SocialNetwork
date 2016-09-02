package com.social.network.services.impl;

import static com.social.network.utils.Constants.ACCEPT_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.DECLINE_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.INVITATION_MESSAGE;

import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.friend.actions.FriendTemplate;
import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.FriendDao;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.exceptions.friend.DeleteFriendException;
import com.social.network.exceptions.friend.InviteAcceptedException;
import com.social.network.exceptions.friend.InviteDeclinedException;
import com.social.network.exceptions.friend.InviteException;
import com.social.network.services.FriendService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii May 16, 2016
 *
 */

@Service
public class FriendServiceImpl implements FriendService {

	private static final Logger logger = LoggerFactory.getLogger(FriendService.class);

	@Autowired
	private UserChatDao userChatDao;
	@Autowired
	private ChatDao chatDao;
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("inviteFriend")
	private FriendTemplate inviteFriend;
	@Autowired
	@Qualifier("acceptInvitation")
	private FriendTemplate acceptInvitation;
	@Autowired
	@Qualifier("declineInvitation")
	private FriendTemplate declineInvitation;

	@Override
	@Transactional(readOnly = true)
	public Set<Friend> getFriends() {
		logger.debug(" getFriends  ");

		return userService.getLoggedUserEntity().getFriends();
	}

	@Override
	@Transactional
	public Friend inviteFriend(long userId) {
		logger.debug(" inviteFriend  userId : {}", userId);

		User loggedUser = userService.getLoggedUserEntity();
		User invitee = userService.getUserById(userId);

		validateAndUpdateStatus(loggedUser.getUserId(), userId, FriendStatus.NEW);

		// Create friend
		Chat chat = chatDao.merge(new Chat());

		Friend friend = friendDao.merge(new Friend(chat, FriendStatus.INVITER, loggedUser, invitee));
		friendDao.merge(new Friend(chat, FriendStatus.INVITEE, invitee, loggedUser));

		// Set chat name
		userChatDao.merge(new UserChat(chat, loggedUser, invitee.getUserFullName()));
		userChatDao.merge(new UserChat(chat, invitee, loggedUser.getUserFullName()));

		inviteFriend.friendAction(INVITATION_MESSAGE, loggedUser, chat);

		return friend;
	}

	@Override
	@Transactional
	public Friend acceptInvitation(long userId) {
		logger.debug(" acceptInvite : userId = {}", userId);
		User loggedUser = userService.getLoggedUserEntity();

		Friend friend = validateAndUpdateStatus(loggedUser.getUserId(), userId, FriendStatus.ACCEPTED);

		acceptInvitation.friendAction(ACCEPT_INVITATION_MESSAGE, loggedUser, friend.getChat());

		return friend;
	}

	@Override
	@Transactional
	public Friend declineInvitation(long userId) {
		logger.debug(" declineInvite : userId = {}", userId);
		User loggedUser = userService.getLoggedUserEntity();

		Friend friend = validateAndUpdateStatus(loggedUser.getUserId(), userId, FriendStatus.DECLINED);

		declineInvitation.friendAction(DECLINE_INVITATION_MESSAGE, loggedUser, friend.getChat());

		return friend;
	}

	@Override
	@Transactional
	public Friend deleteFriend(long userId) {
		// Get loggedUser
		User loggedUser = userService.getLoggedUserEntity();

		logger.debug(" deleteFriend : userId = {}, friendId = {} ", loggedUser.getUserId(), userId);

		Friend friend = validateAndUpdateStatus(loggedUser.getUserId(), userId, FriendStatus.DELETED);

		return friend;
	}

	@Transactional
	public Friend validateAndUpdateStatus(long invitee, long inviter, FriendStatus status) {
		logger.debug(" validateFriendByStatus status {} :", status);

		Friend inviteeFriend = friendDao.findByFriendAndOwner(invitee, inviter);
		Friend inviterFriend = friendDao.findByFriendAndOwner(inviter, invitee);

		switch (status) {
		case NEW:
			if (Objects.nonNull(inviterFriend) || Objects.nonNull(inviteeFriend)) {
				throw new InviteException("invitation exist!");
			}
			break;
		case ACCEPTED:
			if (inviterFriend.getFriendStatus() == FriendStatus.INVITER
					&& inviteeFriend.getFriendStatus() == FriendStatus.INVITEE) {
				updateStatus(inviterFriend, inviteeFriend, FriendStatus.ACCEPTED);
			} else {
				throw new InviteAcceptedException("Error accept invitation");
			}
			break;
		case DECLINED:
			if (inviterFriend.getFriendStatus() == FriendStatus.INVITER
					&& inviteeFriend.getFriendStatus() == FriendStatus.INVITEE) {
				updateStatus(inviterFriend, inviteeFriend, FriendStatus.DECLINED);
			} else {
				throw new InviteDeclinedException("Error decline invitation");
			}
			break;
		case DELETED:
			if (inviterFriend.getFriendStatus() == FriendStatus.ACCEPTED
					&& inviteeFriend.getFriendStatus() == FriendStatus.ACCEPTED) {
				updateStatus(inviterFriend, inviteeFriend, FriendStatus.DELETED);
			} else {
				throw new DeleteFriendException("Error decline invitation");
			}
			break;
		default:
			throw new InviteException("Invite don't exist");
		}
		return inviteeFriend;

	}

	@Transactional
	private void updateStatus(Friend inviterFriend, Friend inviteeFriend, FriendStatus status) {
		inviterFriend.setFriendStatus(status);
		inviteeFriend.setFriendStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isYourFriend(long friend, long owner) {
		Friend inviteeFriend = friendDao.findByFriendAndOwner(friend, owner);
		Friend inviterFriend = friendDao.findByFriendAndOwner(owner, friend);
		if (inviterFriend.getFriendStatus() == FriendStatus.ACCEPTED
				&& inviteeFriend.getFriendStatus() == FriendStatus.ACCEPTED) {
			return true;
		} else {
			return false;
		}
	}
}
