package com.social.network.services;

import java.util.Locale;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.model.Account;
import com.social.network.model.Profile;
import com.social.network.model.User;

/**
 * Created by Yadykin Andrii May 18, 2016
 *
 */
@Transactional
public class InitTest {
    @Autowired
    protected AuthService authService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected SessionFactory sessionFactory;

    protected User user10 = new User("user10", "user10", new Profile("user10", "user10", "Dnepr", "Ukraine", Locale.US));
    protected User user20 = new User("user20", "user20", new Profile("user20", "user20", "Lviv", "Ukraine", Locale.US));
    protected User user30 = new User("Roma", "D",  new Profile("Roma", "D", "Kiev", "Ukraine", Locale.US));
    protected Account account10 = new Account("user10", "user1", "ROLE_USER", user10);
    protected Account account20 = new Account("user20", "user2", "ROLE_USER", user20);
    protected Account account30 = new Account("user30", "user2", "ROLE_USER", user30);
    
    @Before    
    public void InitTest() {
        authService.signup(account10);
        authService.signup(account20);
        authService.signup(account30);

    }
    
    protected void clearSession(){
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}
