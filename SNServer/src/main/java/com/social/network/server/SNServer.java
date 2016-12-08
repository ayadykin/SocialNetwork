package com.social.network.server;

import lombok.extern.log4j.Log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Yadykin Andrii Nov 29, 2016
 *
 */

@Log4j2
@SpringBootApplication
@EnableEurekaServer
public class SNServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        log.debug("Start SNServer");
        SpringApplication.run(SNServer.class, args);
    }
}
