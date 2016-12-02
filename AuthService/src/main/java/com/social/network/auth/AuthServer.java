package com.social.network.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.social.network.auth.config.OAuth2AuthorizationConfig;
import com.social.network.auth.config.WebSecurityConfig;

/**
 * Created by Yadykin Andrii Dec 2, 2016
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@ComponentScan(basePackageClasses = Application.class)
@Import({ WebSecurityConfig.class, OAuth2AuthorizationConfig.class })
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuthServer.class, args);
    }
}
