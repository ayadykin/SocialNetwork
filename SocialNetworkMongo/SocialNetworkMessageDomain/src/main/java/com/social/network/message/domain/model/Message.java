package com.social.network.message.domain.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Document(collection = "message")
public class Message {

    private long messageId;

    private String text;

    private long publisher;

    private boolean hidden;

    private Set<Recipient> recipient = new HashSet<>();

    public Message() {

    }

    public Message(long messageId, String text, long publisher, Set<Recipient> recipient){
        this.messageId = messageId;
        this.text = text;
        this.publisher = publisher;
        this.recipient = recipient;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getPublisher() {
        return publisher;
    }

    public void setPublisher(long publisher) {
        this.publisher = publisher;
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
        return "Message [messageId=" + messageId + ", text=" + text + ", publisher=" + publisher + ", hidden=" + hidden + ", recipient="
                + recipient + "]";
    }

}
