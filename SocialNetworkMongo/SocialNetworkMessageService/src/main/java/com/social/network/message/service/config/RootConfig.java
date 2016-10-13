package com.social.network.message.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.social.network.message.domain.config.MongoConfig;
import com.social.network.message.service.Application;
import com.social.network.message.service.CounterService;
import com.social.network.message.service.impl.CounterServiceImpl;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Configuration
@ComponentScan(basePackageClasses = Application.class)
@Import({ MongoConfig.class })
public class RootConfig {
    
    @Bean
    public CounterService counterService(MongoTemplate mongoTemplate) {
        return new CounterServiceImpl(mongoTemplate);

    }
}
