package com.social.network.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.config.ApplicationConfig;
import com.social.network.config.RedisConfig;
import com.social.network.config.SecurityConfig;
import com.social.network.domain.config.HibernateConfig;
import com.social.network.domain.dao.AccountDao;
import com.social.network.domain.dao.ProfileDao;
import com.social.network.domain.dao.UsersDao;
import com.social.network.domain.model.Account;
import com.social.network.domain.model.Profile;
import com.social.network.domain.model.User;
import com.social.network.services.AuthService;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, HibernateConfig.class, SecurityConfig.class, RedisConfig.class}, loader = AnnotationConfigContextLoader.class)
public class SignUpTest {

    @Autowired
    AccountDao accountDao;

    @Autowired
    UsersDao usersDao;

    @Autowired
    ProfileDao profileDao;

    @Autowired
    AuthService authService;

    @Test
    public void testAdd() {

        /*User user = new User(new Profile("Name", "Surname", "City", "Country", Locale.US));
        Account account1 = new Account("email", "pass", "ROLE_USER", user);
        authService.signup(account1);*/

        //assertTrue(accountDao.exists(account1.getAccountId()));
        //assertEquals(user, usersDao.get(user.getUserId()));
        //assertEquals(user.getProfile().getFirstName(), profileDao.get(user.getUserId()).getFirstName());
    }
}