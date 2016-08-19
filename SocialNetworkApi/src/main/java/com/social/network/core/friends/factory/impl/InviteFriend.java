package com.social.network.core.friends.factory.impl;

import static com.social.network.utils.Constants.INVITATION_MESSAGE;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.builder.system.impl.InviteFriendMessage;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii Aug 11, 2016
 *
 */

@Component
public class InviteFriend extends FriendSrtategy {
    private static final Logger logger = LoggerFactory.getLogger(InviteFriend.class);

    @Autowired
    protected InviteFriendMessage inviteFriendMessage;
    @Autowired
    private UserChatDao userChatDao;

    @Override
    @Transactional
    public Message action(long userId) {
        setUserId(userId);

        createFriendValidation(loggedUser, invitee);
        Set<User> usersList = new HashSet<>();
        usersList.add(loggedUser);
        usersList.add(invitee);
        // Create friend
        Chat chat = chatDao.merge(new Chat(usersList));

        friendDao.merge(new Friend(chat, FriendStatus.INVITER, loggedUser, invitee));
        friendDao.merge(new Friend(chat, FriendStatus.INVITEE, invitee, loggedUser));

        // Set chat name
        UserChat loggedUserChat = userChatDao.findByChatAndUser(chat.getChatId(), loggedUser.getUserId());
        loggedUserChat.setChatName(invitee.getUserFullName());
        UserChat inviteeUserChat = userChatDao.findByChatAndUser(chat.getChatId(), invitee.getUserId());
        inviteeUserChat.setChatName(loggedUser.getUserFullName());

        return messageBuilder.setMessageBuilder(inviteFriendMessage).createOneParamMessage(INVITATION_MESSAGE, loggedUser, chat);
    }

    private void createFriendValidation(User inviter, User invitee) {
        logger.debug(" createFriendValidation :");
        Friend friendEntity = friendDao.findByFriendAndOwner(invitee, inviter);

        if (Objects.nonNull(friendEntity)) {
            throwInviteStatusException(friendEntity.getFriendStatus());
        }
    }

}
