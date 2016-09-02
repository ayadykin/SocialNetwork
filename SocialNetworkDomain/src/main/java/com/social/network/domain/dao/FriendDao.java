package com.social.network.domain.dao;

import com.social.network.domain.model.Friend;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
public interface FriendDao extends GenericDao<Friend, Long> {
    
    Friend findByFriendAndOwner(long invitee, long inviter);
    
}
