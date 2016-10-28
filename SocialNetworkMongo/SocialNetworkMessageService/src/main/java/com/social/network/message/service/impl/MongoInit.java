package com.social.network.message.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.message.domain.model.MongoMessage;
import com.social.network.message.domain.repository.MessageRepository;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Service
public class MongoInit {
    
    private static final Logger logger = LoggerFactory.getLogger(MongoInit.class);
    
    @Autowired
    private MessageRepository messageRepository;

    //@PostConstruct
    //@Transactional
    public void init() {
        logger.debug("----------");
        MongoMessage message = new MongoMessage();
        //messageRepository.save(message);
        
    }
}
