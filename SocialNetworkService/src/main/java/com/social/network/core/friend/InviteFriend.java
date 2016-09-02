package com.social.network.core.friend;

import org.springframework.stereotype.Component;

import com.social.network.core.FriendModel;
import com.social.network.core.FriendTemplate;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.SystemMessageStatus;

@Component(value="inviteFriend")
public class InviteFriend extends FriendTemplate {

	@Override
	public FriendModel callService(long userId) {
		return friendService.inviteFriend(userId);		
	}

	@Override
	public Message createMessage(String messageText, User publisher, Chat chat) {
		return messageService.createSystemMessage(messageText, publisher, chat, SystemMessageStatus.INVITE);

		
	}

}
