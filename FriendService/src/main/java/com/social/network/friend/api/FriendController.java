package com.social.network.friend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.friend.client.FriendClient;
import com.social.network.friend.dto.InviteDto;

/**
 * Created by Yadykin Andrii Nov 30, 2016
 *
 */

@RestController
@RequestMapping("/friend")
public class FriendController {
    
    @Autowired
    private FriendClient friendClient;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void getFriends() {
        // return redisService.getMessage();
    }

    @PostMapping(value = "/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public void inviteFriend(@RequestBody InviteDto inviteDto) {
        friendClient.inviteFriend(inviteDto);
    }
}
