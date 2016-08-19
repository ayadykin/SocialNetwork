package com.social.network.core.friends.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.social.network.core.friends.factory.impl.AcceptFriend;
import com.social.network.core.friends.factory.impl.DeclineFriend;
import com.social.network.core.friends.factory.impl.InviteFriend;
import com.social.network.domain.model.Message;

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

    /**
     * Set friend action
     * 
     * @param friendAction
     * @param userId
     * @return
     */
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
