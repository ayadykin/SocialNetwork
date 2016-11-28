package com.social.network.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}
}
