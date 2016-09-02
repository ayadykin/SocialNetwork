package com.social.network.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.domain.model.Account;
import com.social.network.domain.model.Profile;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.Role;
import com.social.network.rest.dto.SignupFormDto;
import com.social.network.services.AuthService;

/**
 * Created by Yadykin Andrii Sep 2, 2016
 *
 */

@RestController
@RequestMapping(value = "/signup")
public class SignupApi {
    
    private static final Logger logger = LoggerFactory.getLogger(SignupApi.class);
    
    @Autowired
    private AuthService authService;
    
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String signup(@RequestBody SignupFormDto signupFormDto) {

        User user = new User("", "", "", new Profile());
        Account account = new Account(signupFormDto.getEmail(), signupFormDto.getPassword(), Role.ROLE_USER, user);
        if (!authService.signup(account)) {

        }
        authService.signin(account);
        return "resp";
    }
}

