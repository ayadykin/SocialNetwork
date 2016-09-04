package com.social.network.core.friend.actions;

import org.springframework.stereotype.Component;

import com.social.network.core.friend.FriendTemplateMethod;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.exceptions.friend.DeleteFriendException;

@Component(value = "deleteFriend")
public class DeleteFriend extends FriendTemplateMethod{

	@Override
	public void validateStatus(Friend inviterFriend, Friend inviteeFriend, FriendStatus status) {
		if (inviterFriend.getFriendStatus() != FriendStatus.ACCEPTED
				|| inviteeFriend.getFriendStatus() != FriendStatus.ACCEPTED) {
			throw new DeleteFriendException("Error decline invitation");
		}
	}

	@Override
	public Message createMessage(String messageText, User publisher, Chat chat) {
		return messageService.createSystemMessage(messageText, publisher, chat, SystemMessageStatus.SYSTEM);
	}

}
