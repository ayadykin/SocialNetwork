package com.social.network.rest.dto;

import java.io.Serializable;
import java.util.Date;

import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.utils.Constants;

/**
 * Created by Yadykin Andrii May 19, 2016
 *
 */

public class MessageDto implements Serializable {

    private long chatId;
    private long messageId;
    private String text;
    private Date date;
    private long ownerId;
    private String ownerName;
    private boolean hidden;
    private SystemMessageStatus messageInviteStatus;

    public MessageDto() {
    }

    public MessageDto(long chatId, long messageId, String text, Date date, String ownerName, long ownerId) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.text = text;
        this.date = date;
        this.ownerName = ownerName;
        this.ownerId = ownerId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
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
        return Constants.dateFormat.format(date);
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

    public SystemMessageStatus getMessageInviteStatus() {
        return messageInviteStatus;
    }

    public void setMessageInviteStatus(SystemMessageStatus messageInviteStatus) {
        this.messageInviteStatus = messageInviteStatus;
    }

    @Override
    public String toString() {
        return "MessageDto [chatId=" + chatId + ", messageId=" + messageId + ", text=" + text + ", date=" + date
                + ", ownerId=" + ownerId + ", ownerName=" + ownerName + ", hidden=" + hidden
                + ", messageInviteStatus=" + messageInviteStatus + "]";
    }

}
