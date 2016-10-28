package com.social.network.core.friend.actions;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.social.network.core.friend.FriendTemplateMethod;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.exceptions.friend.InviteException;

@Component(value="inviteFriend")
public class InviteFriend extends FriendTemplateMethod {

	@Override
	public void validateStatus(Friend inviterFriend, Friend inviteeFriend, FriendStatus status) {
		if (Objects.nonNull(inviterFriend) || Objects.nonNull(inviteeFriend)) {
			throw new InviteException("invitation exist!");
		}
	}
	
	/*@Override
	public Message createMessage(String messageText, User publisher, Chat chat) {
		return messageService.createSystemMessage(messageText, publisher, chat, SystemMessageStatus.INVITE);	
	}*/

}
