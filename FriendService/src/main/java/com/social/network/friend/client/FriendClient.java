package com.social.network.friend.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.social.network.friend.dto.InviteDto;
import com.social.network.friend.fallback.FriendClientFallBack;

/**
 * Created by Yadykin Andrii Dec 1, 2016
 *
 */

@FeignClient(name = "neo4j-service", fallback = FriendClientFallBack.class)
public interface FriendClient {

    @RequestMapping(method = RequestMethod.POST, value = "/SocialNetworkNeo4j/friend/invite")
    long inviteFriend(@RequestBody InviteDto inviteDto);
    
}
