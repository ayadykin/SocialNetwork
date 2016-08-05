package com.social.network.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dto.FriendDto;
import com.social.network.dto.MessageDto;
import com.social.network.model.Friend;
import com.social.network.model.Message;
import com.social.network.services.FriendService;
import com.social.network.services.RedisService;
import com.social.network.utils.EntityToDtoMapper;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Service
public class FriendServiceFacade {
    @Autowired
    private FriendService friendService;
    @Autowired
    private RedisService redisService;

    @Transactional
    public List<FriendDto> getFriends() {
        List<FriendDto> friendsDto = new ArrayList<>();
        for (Friend friend : friendService.getFriends()) {
            friendsDto.add(EntityToDtoMapper.convertFriendToFriendDto(friend));
        }
        return friendsDto;
    }

    @Transactional
    public boolean inviteFriend(long userId) {
        // Invite friend
        Message message = friendService.inviteFriend(userId);
        // Send message to redis
        return sendMessageToRedis(message, userId);
    }

    @Transactional
    public boolean acceptInvitation(long userId) {
        // Accept invitation
        Message message = friendService.acceptInvitation(userId);
        // Send message to redis
        return sendMessageToRedis(message, userId);
    }

    @Transactional
    public boolean declineInvitation(long userId) {
        // Decline invitation
        Message message = friendService.declineInvitation(userId);
        // Send message to redis
        return sendMessageToRedis(message, userId);
    }

    @Transactional
    public boolean deleteFriend(long friendId) {
        return friendService.deleteFriend(friendId);
    }

    private boolean sendMessageToRedis(Message message, long userId) {
        MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message, userId);
        return redisService.sendMessageToRedis(messageDto);
    }
}
