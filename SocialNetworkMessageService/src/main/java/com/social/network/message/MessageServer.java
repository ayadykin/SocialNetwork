package com.social.network.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Yadykin Andrii Nov 18, 2016
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackageClasses = Application.class)
public class MessageServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        // System.setProperty("spring.config.name", "message-service");
        SpringApplication.run(MessageServer.class, args);
    }
}
