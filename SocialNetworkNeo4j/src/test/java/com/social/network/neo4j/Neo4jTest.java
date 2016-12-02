package com.social.network.neo4j;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.neo4j.domain.User;
import com.social.network.neo4j.repository.UserRepository;
import com.social.network.neo4j.services.Neo4jService;

import static org.junit.Assert.*;

/**
 * Created by Yadykin Andrii Nov 30, 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Neo4jServer.class)
@Transactional
public class Neo4jTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Neo4jService neo4jService;
    
    @Before
    public void init() {
        assertNotNull(userRepository);
    }

    @Test
    public void test() {
        User user = neo4jService.createUser("test");
        
        assertNotNull(user.getId());
    }
    
    @Test
    public void testInvite() {
        User user1 = neo4jService.createUser("test");
        User user2 = neo4jService.createUser("test");
        
        neo4jService.inviteFriend(user1.getId(), user2.getId());
        
        neo4jService.acceptInvitation(user2.getId(), user1.getId());
        
        //assertTrue(user1.getInvetee().isEmpty());
        
        //assertEquals(1, user1.getChats().size());
        //assertEquals(1, user2.getChats().size());
    }
}
