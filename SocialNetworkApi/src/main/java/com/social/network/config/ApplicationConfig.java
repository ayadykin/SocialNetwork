package com.social.network.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import com.social.network.Application;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({ Controller.class,
        Configuration.class }))
public class ApplicationConfig {
    private static final String MESSAGE_SOURCE = "classpath:i18n/messages";
    private static final String PERSISTANCE_PROPERTIES = "/persistance.properties";
    private static final String REDIS_PROPERTIES = "/redis.properties";
    private static final String AZURE_PROPERTIES = "/translate.properties";

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new Resource[] { new ClassPathResource(PERSISTANCE_PROPERTIES),
                new ClassPathResource(REDIS_PROPERTIES), new ClassPathResource(AZURE_PROPERTIES) });
        return ppc;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
