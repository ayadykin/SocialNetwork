package com.social.network.facade;

import static com.social.network.utils.Constants.ACCEPT_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.DECLINE_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.INVITATION_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.FriendAnswerMessage;
import com.social.network.core.message.text.MessageTextBuilder;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.dto.FriendDto;
import com.social.network.dto.MessageDto;
import com.social.network.services.FriendService;
import com.social.network.services.MessageService;
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
    @Autowired
    private MessageService messageService;
    @Autowired
    private FriendAnswerMessage friendAnswerMessage;
    @Autowired
    private MessageTextBuilder messageTextBuilder;

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
        Friend friend = friendService.inviteFriend(userId);

        String messageText = messageTextBuilder.createOneParamMessage(INVITATION_MESSAGE, friend.getUser().getUserFullName());

        Message message = messageService.createSystemMessage(messageText, friend.getUser(), friend.getChat(), SystemMessageStatus.INVITE);

        // Send message to redis
        return sendMessageToRedis(message);
    }

    @Transactional
    public boolean acceptInvitation(long userId) {
        // Accept invitation
        Friend friend = friendService.acceptInvitation(userId);

        String messageText = messageTextBuilder.createOneParamMessage(ACCEPT_INVITATION_MESSAGE, friend.getUser().getUserFullName());

        Message message = friendAnswerMessage.createSystemMessage(messageText, friend.getUser(), friend.getChat());

        // Send message to redis
        return sendMessageToRedis(message);
    }

    @Transactional
    public boolean declineInvitation(long userId) {
        // Decline invitation
        Friend friend = friendService.declineInvitation(userId);

        String messageText = messageTextBuilder.createOneParamMessage(DECLINE_INVITATION_MESSAGE, friend.getUser().getUserFullName());

        Message message = friendAnswerMessage.createSystemMessage(messageText, friend.getUser(), friend.getChat());

        // Send message to redis
        return sendMessageToRedis(message);
    }

    @Transactional
    public boolean deleteFriend(long friendId) {
        return friendService.deleteFriend(friendId);
    }

    private boolean sendMessageToRedis(Message message) {
        MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message);
        return redisService.sendMessageToRedis(messageDto);
    }
}
