package com.social.network.neo4j.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Setter
@Getter
@NodeEntity
public class User {

    @GraphId
    private Long id;

    private String name;

    @Relationship(type = "INVITEE")
    private Set<User> invetee = new HashSet<>();
    
    @Relationship(type = "INVITER", direction = Relationship.INCOMING)
    private Set<User> inveter = new HashSet<>();

    @Relationship(type = "FRIEND")
    private Set<User> friends = new HashSet<>();

    @Relationship(type = "GROUP")
    private Set<Group> group = new HashSet<>();

    public void addInvitee(User user) {
        invetee.add(user);
    }

    public boolean removeInvitee(User user) {
        return invetee.remove(user);
    }
    
    public void addInviter(User user) {
        inveter.add(user);
    }

    public boolean removeInviter(User user) {
        return inveter.remove(user);
    }

    public void addFriend(User user) {
        friends.add(user);
    }
    
    public void addGroup(Group user) {
        group.add(user);
    }
}
