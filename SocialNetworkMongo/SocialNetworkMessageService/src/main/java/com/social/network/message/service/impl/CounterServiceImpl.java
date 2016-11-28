package com.social.network.message.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.social.network.message.domain.model.Counter;
import com.social.network.message.service.CounterService;

/**
 * Created by Yadykin Andrii Oct 13, 2016
 *
 */

@Service
public class CounterServiceImpl implements CounterService {

    private final static Logger logger = LoggerFactory.getLogger(CounterService.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long getNextSequence(String counterName) {
        logger.debug("getNextSequence counterName : {}", counterName);
        Query query = new Query(Criteria.where("name").is(counterName));
        Update update = new Update().inc("sequence", 1);

        return mongoTemplate.findAndModify(query, update, Counter.class).getSequence();
    }

}
