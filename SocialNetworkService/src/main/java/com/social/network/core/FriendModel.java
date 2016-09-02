package com.social.network.core;

import com.social.network.domain.model.Chat;
import com.social.network.domain.model.User;

public class FriendModel {

	private User user;
	private Chat chat;

	public FriendModel(User user, Chat chat) {
		this.user = user;
		this.chat = chat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

}
