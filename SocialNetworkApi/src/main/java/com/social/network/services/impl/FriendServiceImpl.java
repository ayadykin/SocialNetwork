package com.social.network.services.impl;

import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.friends.factory.FriendAction;
import com.social.network.core.friends.factory.FriendFactory;
import com.social.network.domain.dao.FriendDao;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.exceptions.friend.DeleteFriendException;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.friend.InviteAcceptedException;
import com.social.network.exceptions.friend.InviteDeclinedException;
import com.social.network.exceptions.friend.InviteException;
import com.social.network.services.FriendService;
import com.social.network.services.UserService;
import com.social.network.validation.DaoValidation;

/**
 * Created by Yadykin Andrii May 16, 2016
 *
 */

@Service
public class FriendServiceImpl implements FriendService {

    private static final Logger logger = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendFactory friendFactory;

    @Override
    @Transactional(readOnly = true)
    public Set<Friend> getFriends() {
        logger.debug(" getFriends  ");

        return userService.getLoggedUserEntity().getFriends();
    }

    @Override
    @Transactional
    public Message inviteFriend(long userId) {
        logger.debug(" inviteFriend  userId : {}", userId);

        return friendFactory.setAction(FriendAction.INVITE, userId);
    }

    @Override
    @Transactional
    public Message acceptInvitation(long userId) {
        logger.debug(" acceptInvite : userId = {}", userId);

        return friendFactory.setAction(FriendAction.ACCEPT, userId);
    }

    @Override
    @Transactional
    public Message declineInvitation(long userId) {
        logger.debug(" declineInvite : userId = {}", userId);

        return friendFactory.setAction(FriendAction.DECLINE, userId);
    }

    @Override
    @Transactional
    public boolean deleteFriend(long friendId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug(" deleteFriend : userId = {}, friendId = {} ", loggedUser.getUserId(), friendId);

        Friend friend = DaoValidation.friendExistValidation(friendDao, friendId);

        if (friend.getFriendStatus() == FriendStatus.ACCEPTED && friend.getUser().getUserId() == loggedUser.getUserId()) {
            friend.getChat().hiddeChat();
            // Update status
            friend.setFriendStatus(FriendStatus.DELETED);
        } else {
            throw new DeleteFriendException("Error delete friend id = " + friendId);
        }

        // Delete my friend
        boolean deleteMyFriend = false;
        for (Friend myFriend : friend.getFriend().getFriends()) {
            if (myFriend.getFriend().getUserId() == loggedUser.getUserId() && myFriend.getFriendStatus() == FriendStatus.ACCEPTED) {
                myFriend.setFriendStatus(FriendStatus.DELETED);
                deleteMyFriend = true;
                break;
            }
        }
        if (!deleteMyFriend) {
            throw new DeleteFriendException("Error delete my friend ");
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Friend validateFriendByStatus(User invitee, User inviter, FriendStatus status) {
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

    private void throwInviteStatusException(FriendStatus status) {
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

}
