package com.social.network.services.impl;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.neo4j.domain.User;
import com.social.network.neo4j.repository.UserRepository;
import com.social.network.services.Neo4jService;

@Service
public class Neo4jServiceImpl implements Neo4jService {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public void save(long id, String name) {
		userRepository.save(new User(id, name));
	}
	
	@Override
	@Transactional
	public void acceptFriend(long id, long to) {
		User user = userRepository.findOne(id);
		User user1 = userRepository.findOne(to);
		//user.getInvitee().remove(user1);
		//user1.getInviter().remove(user1);
		//user.addFriend(user1);
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public void addInvite(long id, long to) {
		User user = userRepository.findOne(id);
		User user1 = userRepository.findOne(to);
		//user.addInviter(user1);
		userRepository.save(user);
		//user1.addInvitee(user);
		userRepository.save(user1);
	}

}
