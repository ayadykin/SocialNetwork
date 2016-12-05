package com.social.network.neo4j.api;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.neo4j.domain.User;
import com.social.network.neo4j.dto.InviteDto;
import com.social.network.neo4j.dto.UserDto;
import com.social.network.neo4j.services.Neo4jService;

/**
 * Created by Yadykin Andrii Nov 30, 2016
 *
 */

@RestController
@RequestMapping("/friend")
public class FriendServiceController {

    @Autowired
    private Neo4jService neo4jService;
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getFriends(@PathVariable("id") long userId){
        Set<User> friends = neo4jService.getFriends(userId);
        
        return friends.stream().map(f-> new UserDto(f.getId(), f.getName())).collect(Collectors.toList());      
    }


    //@PreAuthorize("#oauth2.hasScope('server')")
    @PostMapping(value = "/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public void inviteFriend(@RequestBody InviteDto inviteDto) {

        neo4jService.inviteFriend(inviteDto.getUserId(), inviteDto.getFriendId());
    }

    @PostMapping(value = "/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptInvitation(@RequestBody InviteDto inviteDto) {

        neo4jService.acceptInvitation(inviteDto.getUserId(), inviteDto.getFriendId());
    }
    
    public void declineInvitation(@RequestBody InviteDto inviteDto) {
        
    }
    
    public void deleteFriend(@RequestBody InviteDto inviteDto) {
        
    }
}
