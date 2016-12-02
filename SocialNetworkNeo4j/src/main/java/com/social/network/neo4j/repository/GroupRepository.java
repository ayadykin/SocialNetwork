package com.social.network.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.social.network.neo4j.domain.Group;

@Repository
public interface GroupRepository extends GraphRepository<Group> {

}
