package com.social.network.auth;

import lombok.extern.log4j.Log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.social.network.auth.config.OAuth2AuthorizationConfig;
import com.social.network.auth.config.WebSecurityConfig;

/**
 * Created by Yadykin Andrii Dec 2, 2016
 *
 */

@Log4j2
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableResourceServer
@EnableAuthorizationServer
@ComponentScan(basePackageClasses = Application.class)
@Import({ WebSecurityConfig.class, OAuth2AuthorizationConfig.class })
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        log.debug("Start AuthServer");
        SpringApplication.run(AuthServer.class, args);
    }
}
