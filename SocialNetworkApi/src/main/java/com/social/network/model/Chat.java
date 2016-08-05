package com.social.network.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;

import com.social.network.model.labels.CreationLabel;
import com.social.network.model.labels.HiddenLabel;

/**
 * Created by Yadykin Andrii May 11, 2016
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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

    @OrderBy("userId")
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE })
    @ManyToMany
    @JoinTable(name = "user_chat", joinColumns = { @JoinColumn(name = "chatId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
    private List<User> users = new ArrayList<>();

    public Chat() {

    }

    public Chat(List<User> users) {
        this.users = users;
        this.creation = new CreationLabel();
        this.hidden = new HiddenLabel();
    }

    public Chat getChat() {
        return this;
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

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
