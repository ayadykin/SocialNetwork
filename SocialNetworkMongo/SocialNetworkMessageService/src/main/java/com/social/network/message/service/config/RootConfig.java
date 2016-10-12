package com.social.network.message.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.social.network.message.domain.config.MongoConfig;
import com.social.network.message.service.Application;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Configuration
@ComponentScan(basePackageClasses = Application.class)
@Import({ MongoConfig.class })
public class RootConfig {

}
