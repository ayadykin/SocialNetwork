package com.social.network.neo4j.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class User {

	Long id;

	String name;
	@Relationship(type = "INVITE")
	List<User> invete = new ArrayList<>();
	
	@Relationship(type = "FRIEND")
	List<User> friends = new ArrayList<>();

	@Relationship(type = "GROUP")
	List<Group> group= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public void addFriend(User friend) {
		this.friends.add(friend);
	}

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}

}
