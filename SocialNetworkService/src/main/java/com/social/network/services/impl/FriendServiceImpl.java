package com.social.network.services.impl;

import static com.social.network.utils.Constants.ACCEPT_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.DECLINE_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.DELETE_FRIEND_MESSAGE;
import static com.social.network.utils.Constants.INVITATION_MESSAGE;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.friend.FriendTemplateMethod;
import com.social.network.domain.dao.FriendDao;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.enums.FriendStatus;
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
	private FriendDao friendDao;
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("inviteFriend")
	private FriendTemplateMethod inviteFriend;
	@Autowired
	@Qualifier("acceptInvitation")
	private FriendTemplateMethod acceptInvitation;
	@Autowired
	@Qualifier("declineInvitation")
	private FriendTemplateMethod declineInvitation;
	@Autowired
	@Qualifier("deleteFriend")
	private FriendTemplateMethod deleteFriend;

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

		return inviteFriend.friendAction(INVITATION_MESSAGE, userId, FriendStatus.NEW);
	}

	@Override
	@Transactional
	public Friend acceptInvitation(long userId) {
		logger.debug(" acceptInvite : userId = {}", userId);

		return acceptInvitation.friendAction(ACCEPT_INVITATION_MESSAGE, userId, FriendStatus.ACCEPTED);
	}

	@Override
	@Transactional
	public Friend declineInvitation(long userId) {
		logger.debug(" declineInvite : userId = {}", userId);

		return declineInvitation.friendAction(DECLINE_INVITATION_MESSAGE, userId, FriendStatus.DECLINED);
	}

	@Override
	@Transactional
	public Friend deleteFriend(long userId) {
		logger.debug(" deleteFriend : userId = {} ", userId);

		return deleteFriend.friendAction(DELETE_FRIEND_MESSAGE, userId, FriendStatus.DELETED);
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
