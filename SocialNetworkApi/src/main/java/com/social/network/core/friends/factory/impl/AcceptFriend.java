package com.social.network.core.friends.factory.impl;

import static com.social.network.utils.Constants.ACCEPT_INVITATION_MESSAGE;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.builder.system.impl.FriendAnswerMessage;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii Aug 11, 2016
 *
 */
@Component
public class AcceptFriend extends FriendSrtategy {
    @Autowired
    private FriendAnswerMessage friendAnswerMessage;

    @Override
    @Transactional
    public Message action(long userId) {
        setUserId(userId);

        Friend inviterFriend = validateFriendByStatus(loggedUser, invitee, FriendStatus.INVITEE);
        Friend inviteeFriend = validateFriendByStatus(invitee, loggedUser, FriendStatus.INVITER);

        // Update status
        inviterFriend.setFriendStatus(FriendStatus.ACCEPTED);
        inviteeFriend.setFriendStatus(FriendStatus.ACCEPTED);

        Set<User> usersList = new HashSet<>();
        usersList.add(loggedUser);
        usersList.add(invitee);

        return messageBuilder.setMessageBuilder(friendAnswerMessage).createOneParamMessage(ACCEPT_INVITATION_MESSAGE, loggedUser,
                inviteeFriend.getChat());
    }
}
