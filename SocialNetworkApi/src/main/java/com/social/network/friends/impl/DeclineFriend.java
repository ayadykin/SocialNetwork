package com.social.network.friends.impl;

import static com.social.network.utils.Constants.DECLINE_INVITATION_MESSAGE;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.message.system.impl.FriendAnswerMessage;
import com.social.network.model.Friend;
import com.social.network.model.Message;
import com.social.network.model.User;
import com.social.network.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii Aug 11, 2016
 *
 */
@Component
public class DeclineFriend extends FriendSrtategy {
    @Autowired
    private FriendAnswerMessage friendAnswerMessage;

    @Override
    @Transactional
    public Message action(long userId) {
        // Validate friend status
        Friend inviterFriend = validateFriendByStatus(loggedUser, invitee, FriendStatus.INVITEE);
        Friend inviteeFriend = validateFriendByStatus(invitee, loggedUser, FriendStatus.INVITER);

        // Update status
        inviterFriend.setFriendStatus(FriendStatus.DECLINED);
        inviteeFriend.setFriendStatus(FriendStatus.DECLINED);
        
        Set<User> usersList = new HashSet<>();
        usersList.add(loggedUser);
        usersList.add(invitee);
        
        // Create message
        return messageBuilder.setMessageBuilder(friendAnswerMessage).createOneParamMessage(DECLINE_INVITATION_MESSAGE,
                loggedUser, usersList, inviteeFriend.getChat());
    }
}
