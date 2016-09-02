package com.social.network.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.social.network.rest.Application;


/**
 * Created by Yadykin Andrii Sep 2, 2016
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = Application.class)
public class RootRestConfig {

}

