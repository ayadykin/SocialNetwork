package com.social.network.message.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Document(collection = "message")
public class MongoMessage {

    private long messageId;

    private Date createDate;

    private String text;

    private long publisherId;

    private boolean hidden;

    private Set<Recipient> recipient = new HashSet<>();

    public MongoMessage() {

    }

    public MongoMessage(long messageId, String text, long publisherId, Set<Recipient> recipient) {
        this.messageId = messageId;
        this.createDate = new Date();
        this.text = text;
        this.publisherId = publisherId;
        this.recipient = recipient;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Set<Recipient> getRecipient() {
        return recipient;
    }

    public void setRecipient(Set<Recipient> recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MongoMessage [messageId=" + messageId + ", createDate=" + createDate + ", text=" + text + ", publisherId=" + publisherId
                + ", hidden=" + hidden + ", recipient=" + recipient + "]";
    }

}
