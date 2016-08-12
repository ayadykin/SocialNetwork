package com.social.network.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.config.ApplicationConfig;
import com.social.network.config.HibernateConfig;
import com.social.network.config.RedisConfig;
import com.social.network.config.SecurityConfig;
import com.social.network.dao.ChatDao;
import com.social.network.dao.GroupDao;
import com.social.network.dao.MessageDao;
import com.social.network.dao.RecipientDao;
import com.social.network.dao.UsersDao;
import com.social.network.model.Chat;
import com.social.network.model.Group;
import com.social.network.model.Message;
import com.social.network.model.Recipient;
import com.social.network.model.User;

/**
 * Created by andrii.perylo on 5/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, HibernateConfig.class, SecurityConfig.class, RedisConfig.class}, loader = AnnotationConfigContextLoader.class)
public class UsersChainTest {

    @Autowired
    UsersDao usersDao;


    @Autowired
    GroupDao groupsDao;

    @Autowired
    ChatDao chatDao;

    @Autowired
    MessageDao messageDao;

    @Autowired
    RecipientDao recipientsDao;

    @Ignore
    @Test
    @Transactional
    public void testCascade() {

        User user1 = new User();

        Chat chat = new Chat();
        Message message = new Message("", user1, chat);
        Recipient recipients = new Recipient(user1, message.getMessageId());
        //message.getRecipient().add(recipients);
        //chat.getMessages().add(message);

        Set<Chat> chatList = user1.getUserChats();
        chatList.add(chat);

        Group group = new Group();
        group.setAdminId(user1.getUserId());
        group.setGroupName("test");


        usersDao.save(user1);

        assertEquals(group, groupsDao.get(group.getChatId()));
        assertEquals(chat, chatDao.get(chat.getChatId()));
        assertEquals(message, messageDao.get(message.getMessageId()));
        assertEquals(recipients, recipientsDao.get(recipients.getResipientId()));
    }
}
