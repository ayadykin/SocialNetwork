package com.social.network.message.domain.config;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories("com.social.network.message.domain.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Override
    protected String getDatabaseName() {
        return "message";
    }

    @Override
    public Mongo mongo() throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress("ds035026.mlab.com", 35026);
        MongoCredential credential = MongoCredential.createCredential("SNmongoDB", getDatabaseName(), "SNpass".toCharArray());
        return new MongoClient(serverAddress, Arrays.asList(credential));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.social.network.message.domain.model";
    }
}
