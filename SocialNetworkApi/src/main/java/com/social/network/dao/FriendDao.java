package com.social.network.dao;

import java.util.List;

import com.social.network.model.Friend;
import com.social.network.model.Group;
import com.social.network.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
public interface FriendDao extends GenericDao<Friend, Long> {
    
    Friend findByFriendAndOwner(User invitee, User inviter);
    
    List<User> findFriendNotInGroup(Group group, User user);
}
