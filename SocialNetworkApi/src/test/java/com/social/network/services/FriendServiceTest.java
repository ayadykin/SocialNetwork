package com.social.network.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.social.network.config.ApplicationConfig;
import com.social.network.config.HibernateConfig;
import com.social.network.config.RedisConfig;
import com.social.network.config.SecurityConfig;
import com.social.network.dto.FriendDto;
import com.social.network.exceptions.friend.DeleteFriendException;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.friend.InviteAcceptedException;
import com.social.network.exceptions.friend.InviteDeclinedException;
import com.social.network.exceptions.friend.InviteException;
import com.social.network.exceptions.user.UserNotExistException;
import com.social.network.model.Friend;
import com.social.network.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii May 17, 2016
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class, HibernateConfig.class, SecurityConfig.class,
        RedisConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FriendServiceTest extends InitTest {

    @Autowired
    private FriendService friendService;

    @Test
    public void testInviteFriend() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());

        clearSession();
        
        assertEquals(1, friendService.getFriends().size());
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.INVITER));
        
        authService.signin(account20);
        assertEquals(1, friendService.getFriends().size());
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.INVITEE));
    }

    @Test
    public void testAcceptInvitation() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());

        authService.signin(account20);
        friendService.acceptInvitation(user10.getUserId());
        
        clearSession();
        
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.ACCEPTED));
        authService.signin(account10);
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.ACCEPTED));
    }

    @Test
    public void testDeclineFriend() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());

        authService.signin(account20);
        friendService.declineInvitation(user10.getUserId());
        
        clearSession();
        
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.DECLINED));

        authService.signin(account10);
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.DECLINED));
    }

    @Test
    public void testDeleteFriend() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());

        authService.signin(account20);
        friendService.acceptInvitation(user10.getUserId());
        
        clearSession();

        friendService.deleteFriend(friendService.getFriends().iterator().next().getFriendId());
        
        clearSession();
        
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus() == FriendStatus.DELETED);
        authService.signin(account10);
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus() == FriendStatus.DELETED);
    }

    @Test
    public void testGetFriends() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());

        clearSession();
        
        assertEquals(1, friendService.getFriends().size());
        assertFalse(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.ACCEPTED));

        authService.signin(account20);
        friendService.acceptInvitation(user10.getUserId());
        assertTrue(friendService.getFriends().iterator().next().getFriendStatus().equals(FriendStatus.ACCEPTED));
    }

    @Test(expected = InviteException.class)
    public void testInviteFriendExceptions() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        
        clearSession();
        
        friendService.acceptInvitation(user20.getUserId());
    }
    
    @Test(expected = DeleteFriendException.class)
    public void testDeleteInvitedFriendExceptions() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        
        clearSession();

        friendService.deleteFriend(friendService.getFriends().iterator().next().getFriendId());
    }
    /**
     * Accept invitation twice
     */
    @Test(expected = InviteAcceptedException.class)
    public void testInviteAcceptedExceptions() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        authService.signin(account20);
        friendService.acceptInvitation(user10.getUserId());
        friendService.acceptInvitation(user10.getUserId());
    }

    @Test(expected = FriendNotExistException.class)
    public void testDeclineFriendException() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        friendService.declineInvitation(user30.getUserId());
    }

    @Test(expected = InviteDeclinedException.class)
    public void testInviteDeclinedException() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());

        authService.signin(account20);
        friendService.declineInvitation(user10.getUserId());
        friendService.declineInvitation(user10.getUserId());
    }

    @Test(expected = UserNotExistException.class)
    public void testUserException() {
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        friendService.declineInvitation(10l);
    }
    
}
