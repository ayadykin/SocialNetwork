package com.social.network.friend.fallback;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.social.network.friend.client.FriendClient;
import com.social.network.friend.dto.InviteDto;

/**
 * Created by Yadykin Andrii Dec 1, 2016
 *
 */

@Slf4j
@Component
public class FriendClientFallBack implements FriendClient {

    @Override
    public long inviteFriend(InviteDto inviteDto) {
        log.error("inviteFriend -> Service not available, inviteDto : {}", inviteDto);
        return 0;
    }
}
