package com.social.network.services.impl;

import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.FriendDao;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.friend.InviteAcceptedException;
import com.social.network.exceptions.friend.InviteDeclinedException;
import com.social.network.exceptions.friend.InviteException;
import com.social.network.services.FriendService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii May 16, 2016
 *
 */

@Service
public class FriendServiceImpl implements FriendService {

    private static final Logger logger = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    private UserChatDao userChatDao;
    @Autowired
    private ChatDao chatDao;
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Set<Friend> getFriends() {
        logger.debug(" getFriends  ");

        return userService.getLoggedUserEntity().getFriends();
    }

    @Override
    @Transactional
    public Friend inviteFriend(long userId) {
        logger.debug(" inviteFriend  userId : {}", userId);

        User loggedUser = userService.getLoggedUserEntity();
        User invitee = userService.getUserById(userId);

        createFriendValidation(loggedUser, invitee);

        // Create friend
        Chat chat = chatDao.merge(new Chat());

        Friend friend = friendDao.merge(new Friend(chat, FriendStatus.INVITER, loggedUser, invitee));
        friendDao.merge(new Friend(chat, FriendStatus.INVITEE, invitee, loggedUser));

        // Set chat name
        chat.addUserChat(userChatDao.merge(new UserChat(chat, loggedUser, invitee.getUserFullName())));
        chat.addUserChat(userChatDao.merge(new UserChat(chat, invitee, loggedUser.getUserFullName())));

        return friend;
    }

    @Override
    @Transactional
    public Friend acceptInvitation(long userId) {
        logger.debug(" acceptInvite : userId = {}", userId);

        return friendAnswer(userId, FriendStatus.ACCEPTED);
    }

    @Override
    @Transactional
    public Friend declineInvitation(long userId) {
        logger.debug(" declineInvite : userId = {}", userId);

        return friendAnswer(userId, FriendStatus.DECLINED);
    }

    @Override
    @Transactional
    public boolean deleteFriend(long userId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        User invitee = userService.getUserById(userId);

        logger.debug(" deleteFriend : userId = {}, friendId = {} ", loggedUser.getUserId(), userId);

        Friend inviterFriend = validateFriendByStatus(loggedUser, invitee, FriendStatus.ACCEPTED);
        Friend inviteeFriend = validateFriendByStatus(invitee, loggedUser, FriendStatus.ACCEPTED);

        // Update status
        inviterFriend.setFriendStatus(FriendStatus.DELETED);
        inviteeFriend.setFriendStatus(FriendStatus.DELETED);

        //throw new DeleteFriendException("Error delete my friend ");

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

    @Transactional
    private Friend friendAnswer(long userId, FriendStatus friendStatus) {
        User loggedUser = userService.getLoggedUserEntity();
        User invitee = userService.getUserById(userId);

        Friend inviterFriend = validateFriendByStatus(loggedUser, invitee, FriendStatus.INVITEE);
        Friend inviteeFriend = validateFriendByStatus(invitee, loggedUser, FriendStatus.INVITER);

        // Update status
        inviterFriend.setFriendStatus(friendStatus);
        inviteeFriend.setFriendStatus(friendStatus);

        return inviterFriend;
    }

    private void createFriendValidation(User inviter, User invitee) {
        logger.debug(" createFriendValidation :");
        Friend friendEntity = friendDao.findByFriendAndOwner(invitee, inviter);

        if (Objects.nonNull(friendEntity)) {
            throwInviteStatusException(friendEntity.getFriendStatus());
        }
    }

}
