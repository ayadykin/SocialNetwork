package com.social.network.friends.impl;

import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.ChatDao;
import com.social.network.dao.FriendDao;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.friend.InviteAcceptedException;
import com.social.network.exceptions.friend.InviteDeclinedException;
import com.social.network.exceptions.friend.InviteException;
import com.social.network.message.builder.MessageBuilder;
import com.social.network.message.system.impl.FriendAnswerMessage;
import com.social.network.message.system.impl.InviteFriendMessage;
import com.social.network.model.Friend;
import com.social.network.model.Message;
import com.social.network.model.User;
import com.social.network.model.enums.FriendStatus;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii Aug 11, 2016
 *
 */

public abstract class FriendSrtategy {

    private static final Logger logger = LoggerFactory.getLogger(FriendSrtategy.class);

    @Autowired
    protected MessageBuilder messageBuilder;
    @Autowired
    protected FriendDao friendDao;
    @Autowired
    protected ChatDao chatDao;
    @Autowired
    private UserService userService;

    protected User loggedUser;
    protected User invitee;

    public void setUserId(long userId) {
        this.loggedUser = userService.getLoggedUserEntity();
        this.invitee = userService.getUserById(userId);
    }
    
    @Transactional(readOnly = true)
    protected Friend validateFriendByStatus(User invitee, User inviter, FriendStatus status) {
        logger.debug(" validateFriendByStatus status {} :", status);

        Friend friendEntity = friendDao.findByFriendAndOwner(invitee, inviter);

        if (Objects.isNull(friendEntity)) {
            throw new FriendNotExistException("Friend not exist !");
        }

        FriendStatus friendStatus = friendEntity.getFriendStatus();

        if (friendStatus == status) {
            return friendEntity;
        }

        throwInviteStatusException(friendStatus);
        return null;

    }
    
    protected void throwInviteStatusException(FriendStatus status) {
        logger.debug(" throwFriendStatusException status : {}", status);
        switch (status) {
        case ACCEPTED:
            throw new InviteAcceptedException("Invite accepted");
        case DECLINED:
            throw new InviteDeclinedException("Invite declined");
        case INVITER:
        case INVITEE:
            throw new InviteException("For status " + status + " you can 'Accept' or 'Decline' invitation!");
        default:
            throw new InviteException("Invite don't exist");
        }
    }
    protected abstract Message action(long userId);

}
