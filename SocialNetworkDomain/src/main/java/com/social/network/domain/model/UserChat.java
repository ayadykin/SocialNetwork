package com.social.network.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.social.network.domain.util.Constants;

/**
 * Created by Yadykin Andrii Jul 12, 2016
 *
 */

@Entity
@Table(name = "user_chat")
@NamedQueries(value = {
		@NamedQuery(name = Constants.FIND_BY_CHAT_AND_USER, query = "from UserChat uc where uc.chat.chatId = :chatId and uc.user.userId =:userId"),
		@NamedQuery(name = Constants.REMOVE_CHAT_AND_USER, query = "delete from UserChat uc where uc.chat = :chat and uc.user =:user") })
public class UserChat implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userChatId;

	@ManyToOne
	@JoinColumn(name = "chatId")
	private Chat chat;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private String chatName;

	public UserChat() {

	}

	public UserChat(Chat chat, User user, String chatName) {
		this.chat = chat;
		this.user = user;
		this.chatName = chatName;
	}

	public long getUserChatId() {
		return userChatId;
	}

	public void setUserChatId(long userChatId) {
		this.userChatId = userChatId;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
