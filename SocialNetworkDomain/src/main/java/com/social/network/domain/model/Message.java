package com.social.network.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.social.network.domain.model.labels.CreationLabel;
import com.social.network.domain.model.labels.HiddenLabel;

/**
 * Created by Yadykin Andrii May 11, 2016
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@FilterDef(name = "messageLimit", parameters = @ParamDef(name = "minDate", type = "date"))
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;

    @NotNull
    @Column
    private String text;
    
    @Embedded
    private CreationLabel createDate;

    @Embedded
    private HiddenLabel hidden;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private User publisher;

    @OneToMany(mappedBy = "messageId")
    private Set<Recipient> recipient;

    @NotNull
    @ManyToOne
    @JoinTable(name = "chat_messages", joinColumns = { @JoinColumn(name = "messageId") }, inverseJoinColumns = {
            @JoinColumn(name = "chatId") })
    private Chat chat;


    public Message() {

    }

    public Message(String text, User publisher, Chat chat) {
        this.text = text;
        this.publisher = publisher;
        this.chat = chat;
        this.createDate = new CreationLabel();
        this.hidden = new HiddenLabel();
    }

    public Chat getChat() {
        return chat;
    }

    public long getChatId() {
        return chat.getChatId();
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate.getCreated();
    }

    public HiddenLabel getHidden() {
        return hidden;
    }

    public void setHidden(HiddenLabel hidden) {
        this.hidden = hidden;
    }

    public User getPublisher() {
        return publisher;
    }

    public long getPublisherId() {
        return publisher.getUserId();
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public Set<Recipient> getRecipient() {
        return recipient;
    }

    public void setRecipient(Set<Recipient> recipients) {
        this.recipient = recipients;
    }

    @Override
    public String toString() {
        return "Message [messageId=" + messageId + ", text=" + text + ", createDate=" + createDate + ", hidden="
                + hidden + ", publisher=" + publisher + ", ]";
    }

}
