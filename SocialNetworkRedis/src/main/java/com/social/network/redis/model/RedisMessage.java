package com.social.network.redis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Yadykin Andrii Sep 2, 2016
 *
 */

public class RedisMessage implements Serializable {
    private long chatId;
    private long messageId;
    private String text;
    private Date date;
    private long ownerId;
    private String ownerName;
    private boolean hidden;
    private Set<Long> recipientsList;
    // private SystemMessageStatus messageInviteStatus;

    public RedisMessage() {
    }
    public RedisMessage(String text, long chatId, String ownerName){
        this.text = text;
        this.chatId = chatId;
        this.ownerName = ownerName;
    }
    public RedisMessage(long messageId, String text, Date date, String ownerName, long ownerId) {
        this.messageId = messageId;
        this.text = text;
        this.date = date;
        this.ownerName = ownerName;
        this.ownerId = ownerId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChat(long chatId) {
        this.chatId = chatId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return "";// Constants.dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /*
     * public SystemMessageStatus getMessageInviteStatus() { return
     * messageInviteStatus; }
     * 
     * public void setMessageInviteStatus(SystemMessageStatus
     * messageInviteStatus) { this.messageInviteStatus = messageInviteStatus; }
     */

    public Set<Long> getRecipientsList() {
        return recipientsList;
    }

    public void setRecipientsList(Set<Long> recipientsList) {
        this.recipientsList = recipientsList;
    }

    @Override
    public String toString() {
        return "RedisMessage [chatId=" + chatId + ", messageId=" + messageId + ", text=" + text + ", date=" + date + ", ownerId=" + ownerId
                + ", ownerName=" + ownerName + ", hidden=" + hidden + ", recipientsList=" + recipientsList + "]";
    }

}
