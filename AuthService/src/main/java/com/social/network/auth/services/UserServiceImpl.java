package com.social.network.auth.services;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.auth.client.UserClient;
import com.social.network.auth.dto.UserDto;
import com.social.network.auth.model.User;
import com.social.network.auth.model.UserRepository;

/**
 * Created by Yadykin Andrii Dec 5, 2016
 *
 */

@Log4j2
@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserClient userClient;

    @Transactional
    public User createUser(String name) {

        long userId = userClient.createUser(new UserDto());
        
        log.info(" createUser userId : {}", userId);

        return userRepository.save(new User(userId, name));

    }
}
