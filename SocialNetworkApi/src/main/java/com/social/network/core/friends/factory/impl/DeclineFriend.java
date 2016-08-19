package com.social.network.core.friends.factory.impl;

import static com.social.network.utils.Constants.DECLINE_INVITATION_MESSAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.builder.system.impl.FriendAnswerMessage;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.enums.FriendStatus;

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

        // Create message
        return messageBuilder.setMessageBuilder(friendAnswerMessage).createOneParamMessage(DECLINE_INVITATION_MESSAGE, loggedUser,
                inviteeFriend.getChat());
    }
}
