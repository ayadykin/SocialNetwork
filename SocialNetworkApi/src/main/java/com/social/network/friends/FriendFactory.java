package com.social.network.friends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.social.network.friends.impl.AcceptFriend;
import com.social.network.friends.impl.DeclineFriend;
import com.social.network.friends.impl.InviteFriend;
import com.social.network.model.Message;

/**
 * Created by Yadykin Andrii Aug 11, 2016
 *
 */

@Component
public class FriendFactory {

    @Autowired
    private InviteFriend inviteFriend;
    @Autowired
    private AcceptFriend acceptFriend;
    @Autowired
    private DeclineFriend declineFriend;

    public Message setAction(FriendAction friendAction, long userId) {

        switch (friendAction) {

        case INVITE:
            return inviteFriend.action(userId);
        case ACCEPT:
            return acceptFriend.action(userId);
        case DECLINE:
            return declineFriend.action(userId);
        default:
            return null;
        }
    }

}
