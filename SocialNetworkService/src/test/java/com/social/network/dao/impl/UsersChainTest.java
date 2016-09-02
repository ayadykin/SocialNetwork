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

import com.social.network.config.RootServiceConfig;
import com.social.network.config.RedisConfig;
import com.social.network.config.SecurityConfig;
import com.social.network.domain.config.HibernateConfig;
import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.GroupDao;
import com.social.network.domain.dao.MessageDao;
import com.social.network.domain.dao.RecipientDao;
import com.social.network.domain.dao.UsersDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.Recipient;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii on 5/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootServiceConfig.class, HibernateConfig.class, SecurityConfig.class, RedisConfig.class}, loader = AnnotationConfigContextLoader.class)
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

        /*User user1 = new User();

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
        assertEquals(recipients, recipientsDao.get(recipients.getResipientId()));*/
    }
}
