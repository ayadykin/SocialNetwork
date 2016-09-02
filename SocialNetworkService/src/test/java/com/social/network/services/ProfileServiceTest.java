package com.social.network.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

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
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.dto.profile.FullProfileDto;
import com.social.network.dto.profile.UserProfileDto;
import com.social.network.exceptions.friend.InviteException;
import com.social.network.exceptions.user.UserNotExistException;

/**
 * Created by Yadykin Andrii May 18, 2016
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootServiceConfig.class, HibernateConfig.class,
        SecurityConfig.class, RedisConfig.class}, loader = AnnotationConfigContextLoader.class)
public class ProfileServiceTest extends InitTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FriendService friendService;

    @Before
    public void setUp() throws InviteException {
        // Invite and accept
        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        authService.signin(account20);
        friendService.acceptInvitation(user10.getUserId());

        // Invite
        authService.signin(account10);
        friendService.inviteFriend(user30.getUserId());

    }

    @Test
    public void testSearchUser() {
        
        clearSession();
        
        List<UserProfileDto> users = profileService.searchProfile(new FullProfileDto("user10", "", "", "", Locale.US, false));
        assertEquals(1, users.size());
        //assertTrue(users.get(0).isYourProfile());

        users = profileService.searchProfile(new FullProfileDto("", "", "Lviv", "", Locale.US, false));
        assertEquals(1, users.size());
        //assertTrue(users.get(0).getFriendStatus() == FriendStatus.ACCEPTED);

        users = profileService.searchProfile(new FullProfileDto("Roma", "", "", "Ukraine", Locale.US, false));
        assertEquals(1, users.size());
        //assertTrue(users.get(0).getFriendStatus() == FriendStatus.INVITER);
    }

    @Test
    public void testGetProfile() {
        assertEquals("user10", profileService.getProfile().getFirstName());
    }

    @Test
    public void testUpdateProfile() {
        FullProfileDto profileDto = profileService.getProfile();
        assertEquals("Dnepr", profileDto.getCity());
        profileDto.setCity("Odessa");
        profileService.updateProfile(profileDto);
        assertEquals("Odessa", profileService.getProfile().getCity());
    }

    @Test
    public void testViewProfile() {
        assertEquals("Lviv", profileService.viewProfile(user20.getUserId()).getCity());
    }

    @Test(expected = UserNotExistException.class)
    public void testUserNotExistException() {
        profileService.viewProfile(1001L);
    }
}
