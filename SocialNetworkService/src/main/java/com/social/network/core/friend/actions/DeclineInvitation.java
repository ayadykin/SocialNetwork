package com.social.network.core.friend.actions;

import org.springframework.stereotype.Component;

import com.social.network.core.friend.FriendTemplateMethod;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.exceptions.friend.DeclineInvitationException;

@Component(value = "declineInvitation")
public class DeclineInvitation extends FriendTemplateMethod {

	@Override
	public void validateStatus(Friend inviterFriend, Friend inviteeFriend, FriendStatus status) {
		if (inviterFriend.getFriendStatus() != FriendStatus.INVITEE
				|| inviteeFriend.getFriendStatus() != FriendStatus.INVITER) {
			throw new DeclineInvitationException("Error decline invitation");
		}
	}

	@Override
	public Message createMessage(String messageText, User publisher, Chat chat) {
		return friendAnswerMessage.createSystemMessage(messageText, publisher, chat);
	}

}
