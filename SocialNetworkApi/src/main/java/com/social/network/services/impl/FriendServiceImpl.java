package com.social.network.services.impl;

import static com.social.network.utils.Constants.ACCEPT_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.DECLINE_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.INVITATION_MESSAGE;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.FriendDao;
import com.social.network.exceptions.friend.DeleteFriendException;
import com.social.network.exceptions.friend.FriendNotExistException;
import com.social.network.exceptions.friend.InviteAcceptedException;
import com.social.network.exceptions.friend.InviteDeclinedException;
import com.social.network.exceptions.friend.InviteException;
import com.social.network.message.Subscribers;
import com.social.network.message.builder.MessageBuilder;
import com.social.network.message.system.impl.FriendAnswerMessage;
import com.social.network.message.system.impl.InviteFriendMessage;
import com.social.network.model.Friend;
import com.social.network.model.Message;
import com.social.network.model.User;
import com.social.network.model.enums.FriendStatus;
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
    private FriendDao friendDao;
    @Autowired
    private UserService userService;
    @Autowired
    private InviteFriendMessage inviteFriendMessage;
    @Autowired
    private FriendAnswerMessage friendAnswerMessage;
    @Autowired
    private MessageBuilder messageBuilder;

    @Override
    @Transactional(readOnly = true)
    public List<Friend> getFriends() {
        // Get logged user
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug(" getFriends for user : {}", loggedUser.getUserId());

        List<Friend> friends = friendDao.findByOwner(loggedUser);

        // Refactor
        friends.stream().forEach(friend -> friend.setFriendName(friend.getUsers().stream().filter(user -> user != loggedUser)
                .map(user -> user.getUserFullName()).findFirst().orElse("No name")));

        return friends;

    }

    @Override
    @Transactional
    public Message inviteFriend(long userId) {
        logger.debug(" inviteFriend  userId : {}", userId);
        User loggedUser = userService.getLoggedUserEntity();
        User invitee = userService.getUserById(userId);

        // Validate friend status
         createFriendValidation(invitee, loggedUser);

        // Create friend
        Friend friend = friendDao.merge(new Friend(FriendStatus.INVITATION, loggedUser, invitee));

        logger.debug(" inviteFriend  friend : {}", friend);

        // Create message
        Message message = messageBuilder.setMessageBuilder(inviteFriendMessage).createOneParamMessage(INVITATION_MESSAGE,
                new Subscribers(loggedUser, invitee), friend);

        return message;
    }

    @Override
    @Transactional
    public Message acceptInvitation(long userId) {
        logger.debug(" acceptInvite : userId = {}", userId);
        User loggedUser = userService.getLoggedUserEntity();
        User inviter = userService.getUserById(userId);

        // Validate friend status
        Friend invitationFriend = validateFriendByStatus(loggedUser, inviter, FriendStatus.INVITATION);

        // Update status
        updateFriendsStatus(invitationFriend, FriendStatus.ACCEPTED);

        // Create message
        return messageBuilder.setMessageBuilder(friendAnswerMessage).createOneParamMessage(ACCEPT_INVITATION_MESSAGE,
                new Subscribers(loggedUser, inviter), invitationFriend);
    }

    @Override
    @Transactional
    public Message declineInvitation(long userId) {
        logger.debug(" declineInvite : userId = {}", userId);
        User loggedUser = userService.getLoggedUserEntity();
        User inviter = userService.getUserById(userId);

        // Validate friend status
        Friend invitedFriend = validateFriendByStatus(loggedUser, inviter, FriendStatus.INVITATION);
        
        // Update status
        updateFriendsStatus(invitedFriend, FriendStatus.DECLINED);

        // Create message
        Message message = messageBuilder.setMessageBuilder(friendAnswerMessage).createOneParamMessage(DECLINE_INVITATION_MESSAGE,
                new Subscribers(loggedUser, inviter), invitedFriend);

        return message;
    }

    @Override
    @Transactional
    public boolean deleteFriend(long friendId) {
        // Get loggedUser
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug(" deleteFriend : userId = {}, friendId = {} ", loggedUser.getUserId(), friendId);

        Friend friend = (Friend) loggedUser.getUserChats().stream().filter(chat -> chat.getChatId() == friendId).findFirst()
                .orElseThrow(() -> new FriendNotExistException("Friend not exist !"));

        if (friend.getFriendStatus() == FriendStatus.ACCEPTED) {
            friend.hiddeChat();
            // Update status
            updateFriendsStatus(friend, FriendStatus.DELETED);
        } else {
            throw new DeleteFriendException("Error delete friend id = " + friendId);
        }

        return true;
    }

    private void updateFriendsStatus(Friend friend, FriendStatus status) {
        logger.debug(" updateFriendsStatus ");
        friend.setFriendStatus(status);
        friendDao.saveOrUpdate(friend);
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

    private void createFriendValidation(User invitee, User inviter) {
        logger.debug(" validateNullFriendStatus :");
        Friend friendEntity = friendDao.findByFriendAndOwner(invitee, inviter);

        if (friendEntity != null) {
            throwInviteStatusException(friendEntity.getFriendStatus());
        }

    }

    private void throwInviteStatusException(FriendStatus status) {
        logger.debug(" throwFriendStatusException status : {}", status);
        switch (status) {
        case ACCEPTED:
            throw new InviteAcceptedException("Invite accepted");
        case DECLINED:
            throw new InviteDeclinedException("Invite declined");
        case INVITATION:
            throw new InviteException("For status " + status + " you can 'Accept' or 'Decline' invitation!");
        default:
            throw new InviteException("Invite don't exist");
        }
    }

}
