package com.social.network.core.friend.actions.impl;

import org.springframework.stereotype.Component;

import com.social.network.core.friend.actions.FriendTemplate;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;

@Component(value="declineInvitation")
public class DeclineInvitation extends FriendTemplate{

	@Override
	public Message createMessage(String messageText, User publisher, Chat chat) {
		return createSystemMessage(messageText, publisher, chat);
	}

}
