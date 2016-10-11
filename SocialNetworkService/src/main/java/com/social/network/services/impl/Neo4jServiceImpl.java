package com.social.network.services.impl;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
		
		User user1 = new User();
		user1.setName(name);
		user1.setId(id);
		userRepository.save(user1);
	}
	
	@Override
	@Transactional
	public void addFriend(long id, long to) {
		User user = userRepository.findOne(id);
		User user1 = userRepository.findOne(to);
		user.addFriend(user1);
		userRepository.save(user);
	}

}
