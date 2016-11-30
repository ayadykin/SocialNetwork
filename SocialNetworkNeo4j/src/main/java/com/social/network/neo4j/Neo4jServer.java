package com.social.network.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Yadykin Andrii Nov 30, 2016
 *
 */
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackageClasses = Application.class)
@EnableTransactionManagement
@EnableNeo4jRepositories
public class Neo4jServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Neo4jServer.class, args);
    }
}


