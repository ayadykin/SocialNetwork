package com.social.network.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.social.network.security.AccountService;
import com.social.network.security.AuthenticationFailure;
import com.social.network.security.AuthenticationSuccess;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService accountService() {
        return new AccountService();
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", accountService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true).userDetailsService(accountService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/", "/favicon.ico", "/resources/**", "/signin", "/signup").permitAll().anyRequest()
                .authenticated().and().csrf().csrfTokenRepository(csrfTokenRepository())
                .and().rememberMe().rememberMeServices(rememberMeServices()).key("remember-me-key")
                .and().formLogin().successHandler(new AuthenticationSuccess())
                .loginPage("/signin").loginProcessingUrl("/j_spring_security_check")
                .passwordParameter("j_password").usernameParameter("j_username").failureHandler(new AuthenticationFailure())
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/signin?logout")
                .and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}