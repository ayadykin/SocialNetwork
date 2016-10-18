package com.social.network.services;

public interface Neo4jService {

	void save(long id, String name);
	
	void acceptFriend(long id, long to);
	
	void addInvite(long id, long to);
}
