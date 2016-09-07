package com.social.network.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.model.Account;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Profile;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.Role;
import com.social.network.services.AuthService;
import com.social.network.services.FriendService;
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
    private FriendService friendFacade;

    @Autowired
    private GroupService groupFacade;

    //@PostConstruct
    @Transactional
    public void Init() {

        logger.debug("-> initialize");

        User user1 = new User("Andrey", "Y", "en",new Profile("", "", "Mikoleav", ""));
        User user2 = new User("Andrey", "P", "en",new Profile("", "", "Mikoleav", ""));
        User user3 = new User("Dima", "D", "en",new Profile("", "", "Kiev", ""));
        User user4 = new User("Viktor", "G", "en",new Profile("", "", "", ""));
        User user5 = new User("Egor", "H", "en",new Profile("", "", "", ""));
        User user6 = new User("Serzh", "F", "ru",new Profile("", "", "", "USA"));
        User user7 = new User("Stas", "G", "en",new Profile("", "", "", ""));

        Account account1 = new Account("user1", "user1", Role.ROLE_USER, user1);
        Account account2 = new Account("user2", "user2", Role.ROLE_USER, user2);
        Account account3 = new Account("user3", "user3", Role.ROLE_USER, user3);
        Account account4 = new Account("user4", "user4", Role.ROLE_USER, user4);
        Account account5 = new Account("user5", "user5", Role.ROLE_USER, user5);
        Account account6 = new Account("user6", "user6", Role.ROLE_USER, user6);
        Account account7 = new Account("user7", "user7", Role.ROLE_USER, user7);
        authService.signup(account1);
        authService.signup(account2);
        authService.signup(account3);
        authService.signup(account4);
        authService.signup(account5);
        authService.signup(account6);
        authService.signup(account7);

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
        Group group = groupFacade.createGroup("test", null);

         groupFacade.addUserToGroup(group.getGroupId(), user2.getUserId());

        // authService.signin(account1);
        // friendService.inviteFriend(user3.getUserId());

    }
}
