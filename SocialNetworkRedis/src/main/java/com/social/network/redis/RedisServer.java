package com.social.network.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.social.network.redis.config.RedisConfig;

/**
 * Created by Yadykin Andrii Nov 28, 2016
 *
 */
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackageClasses = Application.class)
@Import({RedisConfig.class})
public class RedisServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RedisServer.class, args);
    }
}
