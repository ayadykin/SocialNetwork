package com.social.network.domain.dao;

import java.util.List;

import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii on 5/13/2016.
 */
public interface FriendDao extends GenericDao<Friend, Long> {
    
    Friend findByFriendAndOwner(User invitee, User inviter);
    
    List<User> findFriendNotInGroup(Group group, User user);
}
