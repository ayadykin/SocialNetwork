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

import static org.junit.Assert.assertNotNull;

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

    @Before
    public void init() {
        assertNotNull(userRepository);
    }

    @Test
    public void test() {
        User user = new User();
        user.setName("test user2");
        userRepository.save(user);
    }
}
