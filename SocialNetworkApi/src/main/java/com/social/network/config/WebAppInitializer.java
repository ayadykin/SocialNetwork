package com.social.network.config;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by andrii.perylo/Yadykin Andrii on 5/30/2016.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { ApplicationConfig.class, HibernateConfig.class, SecurityConfig.class, RedisConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new DelegatingFilterProxy("springSecurityFilterChain") };
    }

    @Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setAsyncSupported(true);
    }
}
