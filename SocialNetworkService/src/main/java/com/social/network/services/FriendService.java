package com.social.network.services;

import java.util.Set;

import com.social.network.core.FriendModel;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 * Service for user's friends
 */

public interface FriendService {

	/**
	 * Get list of user's friends
	 * 
	 * @return set of friends
	 */
	Set<Friend> getFriends();

	/**
	 * Invite friend
	 * 
	 * @param userId
	 * @return
	 */
	FriendModel inviteFriend(long userId);

	/**
	 * Accept user's invitation
	 * 
	 * @param userId
	 * @return boolean
	 */
	FriendModel acceptInvitation(long userId);

	/**
	 * Decline user's invitation
	 * 
	 * @param userId
	 * @return boolean
	 */
	FriendModel declineInvitation(long userId);

	/**
	 * Delete friend from friend's list
	 * 
	 * @param userId
	 * @return boolean
	 */
	boolean deleteFriend(long userId);

	Friend validateFriendByStatus(User friend, User owner, FriendStatus status);

}
