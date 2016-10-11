package com.social.network.services;

public interface Neo4jService {

	void save(long id, String name);
	
	void addFriend(long id, long to);
}
