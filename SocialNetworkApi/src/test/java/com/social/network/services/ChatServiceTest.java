package com.social.network.services;

import static org.junit.Assert.assertEquals;

import org.hibernate.SessionFactory;
import org.junit.Before;
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
import com.social.network.exceptions.chat.EditMessageException;
import com.social.network.model.Message;
import com.social.network.model.enums.Period;

/**
 * Created by Yadykin Andrii May 23, 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class, HibernateConfig.class, SecurityConfig.class,
        RedisConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ChatServiceTest extends InitTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SessionFactory sessionFactory;
    private long chatId;
    
    @Before
    public void init() {

        authService.signin(account10);
        friendService.inviteFriend(user20.getUserId());
        authService.signin(account20);
        friendService.acceptInvitation(user10.getUserId());
        clearSession();
        chatId = chatService.getChatsList().iterator().next().getChatId();
    }
    
    private void clearSession(){
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
    
    @Test
    public void testGetMessages() {

        assertEquals(1, chatService.getChatsList().size());
        assertEquals(2, chatService.getChatMesasges(chatId, false, Period.ALL).size());
    }

    @Test
    public void testSendMessage() {

        chatService.sendMessage("Send message test", chatId);

        clearSession();
        
        assertEquals(1, chatService.getChatsList().size());
        assertEquals(3, chatService.getChatMesasges(chatId, false, Period.ALL).size());
    }

    @Test
    public void testDeleteMessage() {

        assertEquals(2, chatService.getChatMesasges(chatId, false, Period.ALL).size());

        Message message = chatService.sendMessage("Test", chatId);

        messageService.deleteMessage(message.getMessageId());
        
        clearSession();

        assertEquals(1, chatService.getChatsList().size());
        assertEquals(3, chatService.getChatMesasges(chatId, false, Period.ALL).size());
    }

    @Test
    public void testEditMessage() {

        assertEquals(2, chatService.getChatMesasges(chatId, false, Period.ALL).size());

        Message message = chatService.sendMessage("Test", chatId);

        message = messageService.editMessage(message.getMessageId(), "Test2");
        
        clearSession();

        assertEquals(1, chatService.getChatsList().size());
        assertEquals(3, chatService.getChatMesasges(chatId, false, Period.ALL).size());
        
        for (Message messages : chatService.getChatMesasges(chatId, false, Period.ALL)) {
            if (messages.getMessageId() == message.getMessageId()) {
                assertEquals("Test2", messages.getText());
            }
        }
    }

    @Test(expected = EditMessageException.class)
    public void testEditDeletedMessageException() {
        
        Message message = chatService.sendMessage("Test", chatId);
        messageService.deleteMessage(message.getMessageId());
        messageService.editMessage(message.getMessageId(), "Test2");
    }
    
    @Test(expected = EditMessageException.class)
    public void testEditWrongMessageException() {

        Message message = chatService.sendMessage("Test", chatId);
        authService.signin(account10);
        messageService.editMessage(message.getMessageId(), "Test2");
    }
}
