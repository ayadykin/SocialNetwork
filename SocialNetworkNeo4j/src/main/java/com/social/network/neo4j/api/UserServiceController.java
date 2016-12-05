package com.social.network.neo4j.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.neo4j.dto.UserDto;
import com.social.network.neo4j.services.Neo4jService;

/**
 * Created by Yadykin Andrii Dec 5, 2016
 *
 */

@RestController
@RequestMapping("/user")
public class UserServiceController {

    @Autowired
    private Neo4jService neo4jService;
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDto userDto) {

        neo4jService.createUser(userDto.getName());
    }
}

