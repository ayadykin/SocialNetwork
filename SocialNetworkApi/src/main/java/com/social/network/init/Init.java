package com.social.network.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.network.dto.GroupDto;
import com.social.network.facade.FriendServiceFacade;
import com.social.network.facade.GroupServiceFacade;
import com.social.network.model.Account;
import com.social.network.model.Group;
import com.social.network.model.Profile;
import com.social.network.model.User;
import com.social.network.services.AuthService;
import com.social.network.services.GroupService;

/**
 * Created by Yadykin Andrii Jul 12, 2016
 *
 */
@Service
public class Init {
    private final static Logger logger = LoggerFactory.getLogger(Init.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private FriendServiceFacade friendFacade;

    @Autowired
    private GroupServiceFacade groupFacade;

    @PostConstruct
    @Transactional
    public void Init() {

        logger.debug("-> initialize");

        User user1 = new User("Andrey", "Y", new Profile("", "", "Mikoleav", "", Locale.US));
        User user2 = new User("Andrey", "P", new Profile("", "", "Mikoleav", "", Locale.US));
        User user3 = new User("Dima", "D", new Profile("", "", "Kiev", "", Locale.US));
        User user4 = new User("Viktor", "G", new Profile("", "", "", "", Locale.US));
        User user5 = new User("Egor", "H", new Profile("", "", "", "", Locale.US));

        Account account1 = new Account("user1", "user1", "ROLE_USER", user1);
        Account account2 = new Account("user2", "user2", "ROLE_USER", user2);
        Account account3 = new Account("user3", "user3", "ROLE_USER", user3);
        Account account4 = new Account("user4", "user4", "ROLE_USER", user4);
        Account account5 = new Account("user5", "user5", "ROLE_USER", user5);
        authService.signup(account1);
        authService.signup(account2);
        authService.signup(account3);
        authService.signup(account4);
        authService.signup(account5);

        //Invite
        authService.signin(account1);
        friendFacade.inviteFriend(user2.getUserId());
        friendFacade.inviteFriend(user3.getUserId());
        friendFacade.inviteFriend(user4.getUserId());

        //Accept
        authService.signin(account2);
        friendFacade.acceptInvitation(user1.getUserId());
        
        //Decline
        authService.signin(account3);
        friendFacade.acceptInvitation(user1.getUserId());
        friendFacade.inviteFriend(user4.getUserId());

        // createGroup
        authService.signin(account1);
        String[] usersIdList = { ((Long) user2.getUserId()).toString() };
        GroupDto group = groupFacade.createGroup("test", null);

         groupFacade.addUserToGroup(group.getGroupId(), user2.getUserId());

        // authService.signin(account1);
        // friendService.inviteFriend(user3.getUserId());

    }
}
