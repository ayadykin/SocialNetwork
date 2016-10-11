package com.social.network.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.social.network.neo4j.domain.Group;

public interface FriendRepository extends GraphRepository<Group> {

}
