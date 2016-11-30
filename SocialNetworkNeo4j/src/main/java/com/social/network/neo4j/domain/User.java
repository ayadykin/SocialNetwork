package com.social.network.neo4j.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Relationship(type = "INVITE")
    List<User> invete = new ArrayList<>();

    @Relationship(type = "FRIEND")
    List<User> friends = new ArrayList<>();

    @Relationship(type = "GROUP")
    List<Group> group = new ArrayList<>();

}
