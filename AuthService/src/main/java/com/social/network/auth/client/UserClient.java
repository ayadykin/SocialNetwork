package com.social.network.auth.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.social.network.auth.dto.UserDto;

/**
 * Created by Yadykin Andrii Dec 5, 2016
 *
 */

@FeignClient(name = "neo4j-service")
public interface UserClient {

    @RequestMapping(method = RequestMethod.POST, value = "/SocialNetworkNeo4j/user")
    long createUser(@RequestBody UserDto userDto);
}

