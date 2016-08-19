package com.social.network.services;

import java.util.Set;

import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
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
    Message inviteFriend(long userId);

    /**
     * Accept user's invitation
     * 
     * @param userId
     * @return boolean
     */
    Message acceptInvitation(long userId);

    /**
     * Decline user's invitation
     * 
     * @param userId
     * @return boolean
     */
    Message declineInvitation(long userId);

    /**
     * Delete friend from friend's list
     * 
     * @param friendId
     * @return boolean
     */
    boolean deleteFriend(long friendId);

    Friend validateFriendByStatus(User friend, User owner, FriendStatus status);

}
