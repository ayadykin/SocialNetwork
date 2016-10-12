package com.social.network.message.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.social.network.message.domain.model.Message;
import com.social.network.message.domain.repository.MessageRepository;
import com.social.network.message.service.config.RootConfig;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class }, loader = AnnotationConfigContextLoader.class)
public class MongoTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void init() {

        assertNotNull(messageRepository);
        Message message = new Message();
        List<Message> mes = messageRepository.findAll();
        
        //messageRepository.save(message);

    }
}
