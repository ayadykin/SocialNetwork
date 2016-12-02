package com.social.network.neo4j.services;

import java.util.Set;

import com.social.network.neo4j.domain.Group;
import com.social.network.neo4j.domain.User;

/**
 * Created by Yadykin Andrii Dec 1, 2016
 *
 */

public interface Neo4jService {

    Set<User> getFriends(long userId);

    User createUser(String name);

    void inviteFriend(long userId, long friendId);

    void acceptInvitation(long userId, long friendId);

    Group createGroup(long ownerId, String groupName, long[] users);
}
