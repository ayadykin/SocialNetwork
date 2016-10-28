package com.social.network.message.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.social.network.message.domain.model.MongoMessage;
import com.social.network.message.domain.repository.ChatRepository;
import com.social.network.message.service.config.RootConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class }, loader = AnnotationConfigContextLoader.class)
public class MongoTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MongoChatService mongoChatService;

    @Before
    public void init() {
        assertNotNull(chatRepository);
    }

    @Test
    public void saveChat() {
        mongoChatService.saveChat(10);
    }

    @Test
    public void addMessage() {
        mongoChatService.addMessage(10, "test_message", 1, new HashSet<>(Arrays.asList(1l, 3l)));
    }
    
    @Test
    public void getMessage() {
        List<MongoMessage> mes = mongoChatService.getMessages(10);
    }
}
