package com.social.network.services;

import java.util.Set;

import com.social.network.domain.model.Friend;

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
	Friend inviteFriend(long userId);

	/**
	 * Accept user's invitation
	 * 
	 * @param userId
	 * @return boolean
	 */
	Friend acceptInvitation(long userId);

	/**
	 * Decline user's invitation
	 * 
	 * @param userId
	 * @return boolean
	 */
	Friend declineInvitation(long userId);

	/**
	 * Delete friend from friend's list
	 * 
	 * @param userId
	 * @return boolean
	 */
	Friend deleteFriend(long userId);

	boolean isYourFriend(long friend, long owner);

}
