package com.social.network.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Filter;

import com.social.network.domain.model.labels.CreationLabel;
import com.social.network.domain.model.labels.HiddenLabel;

/**
 * Created by Yadykin Andrii May 11, 2016
 */

@Entity
public class Chat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @Embedded
    private CreationLabel creation;

    @Embedded
    private HiddenLabel hidden;

    @OrderBy("createDate")
    @ManyToMany(mappedBy = "chat")
    @Filter(name = "messageLimit", condition = ":minDate <= createDate")
    private Set<Message> messages = new LinkedHashSet<>();

    // @OrderBy("chatId")
    @OneToMany(mappedBy = "chat")
    private Set<UserChat> userChat = new HashSet<>();

    public Chat() {
        this.creation = new CreationLabel();
        this.hidden = new HiddenLabel();
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<UserChat> getUserChat() {
        return userChat;
    }

    public void setUserChat(Set<UserChat> userChat) {
        this.userChat = userChat;
    }

    public void addUserChat(UserChat userChat) {
        this.userChat.add(userChat);
    }

    public void hiddeChat() {
        this.hidden.setHidden(true);
    }

    public boolean getHidden() {
        return hidden.isHidden();
    }

    public CreationLabel getCreation() {
        return creation;
    }

    @Override
    public String toString() {
        return "Chat [chatId=" + chatId + ", creation=" + creation + ", hidden=" + hidden + "";
    }

}
