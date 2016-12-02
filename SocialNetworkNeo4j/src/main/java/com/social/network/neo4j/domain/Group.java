package com.social.network.neo4j.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Setter
@Getter
@NoArgsConstructor
@NodeEntity
public class Group {

    @GraphId
    private Long id;

    private String name;
    
    @Relationship(type = "OWNER")
    private User owner;

    @Relationship(type = "USER")
    private Set<User> users = new HashSet<>();
    
    public Group(String name, User owner){
        this.name = name;
        this.owner = owner;
    }
    
    public void addUser(User user){
        users.add(user);
    }
}
