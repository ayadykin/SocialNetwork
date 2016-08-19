package com.social.network.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.social.network.config.ApplicationConfig;
import com.social.network.config.RedisConfig;
import com.social.network.config.SecurityConfig;
import com.social.network.domain.config.HibernateConfig;

/**
 * Created by andrii.perylo on 5/25/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class, HibernateConfig.class, SecurityConfig.class,
        RedisConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AuthServiceTest extends InitTest {


    @Test
    public void testSignIn() {
        authService.signin(account10);
        assertEquals(userService.getLoggedUserEntity().getUserId(), user10.getUserId());
        authService.signin(account20);
        assertEquals(userService.getLoggedUserEntity().getUserId(), user20.getUserId());
    }

    @Test
    public void testGetLoggedUser() {
        authService.signin(account10);
        assertEquals(user10, userService.getLoggedUserEntity());
        assertNotEquals(user20, userService.getLoggedUserEntity());
    }

}