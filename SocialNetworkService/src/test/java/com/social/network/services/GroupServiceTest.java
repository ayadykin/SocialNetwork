package com.social.network.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.social.network.domain.model.Group;
import com.social.network.exceptions.chat.ChatRemovedException;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.group.GroupAdminException;
import com.social.network.exceptions.group.GroupPermissionExceptions;

/**
 * Created by Yadykin Andrii on 5/18/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootServiceConfig.class, HibernateConfig.class, SecurityConfig.class,
        RedisConfig.class }, loader = AnnotationConfigContextLoader.class)
public class GroupServiceTest extends InitTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private FriendService friendService;
    
    private Group group;
    
    @Before
    public void createEmptyGroup() {

        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        authService.signin(account20);
        friendService.acceptInvitation(user10.getUserId());
        // String[] usersIdList = new String[1];
        // usersIdList[0] = ((Long) user20.getUserId()).toString();
        authService.signin(account10);
        group = groupService.createGroup("testGroup", null);
        
        clearSession();
    }
    
    @Test
    public void testCreateEmptyGroup() {
        assertEquals(group.getChatId(), groupService.getGroup(group.getChatId()).getChatId());
    }

    @Test
    public void testDeleteGroup() {           
        assertEquals(1, groupService.getGroups().size());
        assertEquals(2, chatService.getChatsList().size());     
        
        groupService.deleteGroup(group.getChatId());
        
        clearSession();
        
        assertTrue(groupService.getGroup(group.getChatId()).getChat().getHidden());     
    }

    @Test
    public void testAddUserToGroup() {
        
        assertEquals(1, groupService.getGroup(group.getChatId()).getChat().getUserChat().size());
        
        groupService.addUserToGroup(group.getChatId(), user20.getUserId());
        authService.signin(account20);
        
        assertEquals(2, groupService.getGroup(group.getChatId()).getChat().getUserChat().size());
    }

    @Test(expected = FriendNotExistException.class)
    public void testAddNotFriendToGroup() {
        groupService.addUserToGroup(group.getChatId(), user30.getUserId());
    }

    @Test
    public void testDeleteUserFromGroup() {
        
        assertEquals(1, groupService.getGroup(group.getChatId()).getChat().getUserChat().size());
        
        groupService.addUserToGroup(group.getChatId(), user20.getUserId());
        
        assertEquals(2, groupService.getGroup(group.getChatId()).getChat().getUserChat().size());

        groupService.deleteUserFromGroup(group.getChatId(), user20.getUserId());

        clearSession();
        
        assertEquals(1, groupService.getGroup(group.getChatId()).getChat().getUserChat().size());
        assertEquals(1, groupService.getGroups().size());
        assertEquals(2, chatService.getChatsList().size());
        
        authService.signin(account20);
        assertEquals(0, groupService.getGroups().size());
        assertEquals(1, chatService.getChatsList().size());
    }

    @Test
    public void testLeaveGroup() {

        groupService.addUserToGroup(group.getChatId(), user20.getUserId());
 
        authService.signin(account20);       
        clearSession();
        
        assertEquals(1, groupService.getGroups().size());      
        //assertEquals(2, groupService.getGroup(group.getChatId()).getChat().getUsers().size());

        groupService.leaveGroup(group.getChatId());       
        clearSession();
        
        assertEquals(0, groupService.getGroups().size());

    }

    @Test(expected = GroupPermissionExceptions.class)
    public void testDeleteUserByNotGroupUserException() {
        authService.signin(account30);
        groupService.deleteUserFromGroup(group.getChatId(), user20.getUserId());
    }

    @Test(expected = GroupAdminException.class)
    public void testDeleteUserByNotAdminException() {
        authService.signin(account20);
        groupService.deleteUserFromGroup(group.getChatId(), user20.getUserId());
    }

    @Test(expected = GroupAdminException.class)
    public void testDeleteAdminException() {
        groupService.deleteUserFromGroup(group.getChatId(), user10.getUserId());
    }

    @Test(expected = GroupPermissionExceptions.class)
    public void testNoSuchUserDeleteException() {
        groupService.deleteUserFromGroup(group.getChatId(), user30.getUserId());
    }

    @Test(expected = GroupAdminException.class)
    public void testNotAdminAddUserException() {
        authService.signin(account20);
        groupService.addUserToGroup(group.getChatId(), user30.getUserId());
    }

    @Test
    public void testAlreadyInGroupAddException() {
        groupService.addUserToGroup(group.getChatId(), user20.getUserId());
    }

    @Test(expected = GroupPermissionExceptions.class)
    public void testGetGroupException() {
        authService.signin(account30);
        groupService.getGroup(group.getChatId());
    }

    @Test(expected = GroupPermissionExceptions.class)
    public void testAdminLeaveGroupException() {
        groupService.leaveGroup(group.getChatId());
    }

    @Test(expected = GroupPermissionExceptions.class)
    public void testNullPointerException() {
        groupService.getGroup(100L);
    }
    
    @Test(expected = ChatRemovedException.class)
    public void testSendMessageToDeletedGroupException() {
        groupService.deleteGroup(group.getChatId());
        chatService.sendMessage("Test", group.getChatId());
    }

    @Test
    public void testNoGroups() {
        authService.signin(account30);
        assertEquals(0, groupService.getGroups().size());
    }
}
