package com.social.network.services;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.social.network.config.RootServiceConfig;
import com.social.network.config.RedisConfig;
import com.social.network.config.SecurityConfig;
import com.social.network.domain.config.HibernateConfig;
import com.social.network.domain.model.Account;
import com.social.network.domain.model.Profile;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii on 5/25/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootServiceConfig.class, HibernateConfig.class,
        SecurityConfig.class, RedisConfig.class}, loader = AnnotationConfigContextLoader.class)
public class UserServiceTest extends InitTest{

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        authService.signin(account10);
    }

    @Test
    public void testGetLoggedUserEntity() {
        assertEquals(user10, userService.getLoggedUserEntity());
    }

    @Test
    public void testGetLoggedUserId() {
        assertEquals(user10.getUserId(), userService.getLoggedUserId());
    }

}