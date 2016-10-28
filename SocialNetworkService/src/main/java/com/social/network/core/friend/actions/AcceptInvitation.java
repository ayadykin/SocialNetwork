package com.social.network.core.friend.actions;

import org.springframework.stereotype.Component;

import com.social.network.core.friend.FriendTemplateMethod;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.exceptions.friend.AcceptInvitationException;

@Component(value = "acceptInvitation")
public class AcceptInvitation extends FriendTemplateMethod {

	@Override
	public void validateStatus(Friend inviterFriend, Friend inviteeFriend, FriendStatus status) {
		if (inviterFriend.getFriendStatus() != FriendStatus.INVITEE
				|| inviteeFriend.getFriendStatus() != FriendStatus.INVITER) {
			throw new AcceptInvitationException("Error accept invitation");
		}
	}

	/*@Override
	public Message createMessage(String messageText, User publisher, Chat chat) {
		return friendAnswerMessage.createSystemMessage(messageText, publisher, chat);
	}*/

}
