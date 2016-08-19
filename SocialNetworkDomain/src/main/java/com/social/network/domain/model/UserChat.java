package com.social.network.domain.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @EmbeddedId
    private UserChatId pk;
    @ManyToOne
    @JoinColumn(name = "chatId", insertable = false, updatable = false)
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    private String chatName;
    
    public UserChatId getPk() {
        return pk;
    }

    public void setPk(UserChatId pk) {
        this.pk = pk;
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
