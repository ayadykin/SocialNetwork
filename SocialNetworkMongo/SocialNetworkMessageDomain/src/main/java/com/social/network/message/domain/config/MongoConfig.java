package com.social.network.message.domain.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories("com.social.network.message.domain.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "message";
    }

    @Override
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient("ds035026.mlab.com", 35026);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }
    @Override
    protected String getMappingBasePackage() {
      return "com.social.network.message.domain.model";
    }
}
