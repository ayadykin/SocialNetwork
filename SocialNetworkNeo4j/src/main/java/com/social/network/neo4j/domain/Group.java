package com.social.network.neo4j.domain;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Group {

	@GraphId
	Long id;
	
	String title;

	/*Person director;

	@Relationship(type = "ACTS_IN", direction = Relationship.INCOMING)
	Set<Person> actors;*/

	@Relationship(type = "MEMBER")
	List<User> members;
	
	@Relationship(type = "FRIEND")
	List<User> friends;
}
