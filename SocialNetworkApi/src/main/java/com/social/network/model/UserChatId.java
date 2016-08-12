package com.social.network.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Created by Yadykin Andrii Jul 12, 2016
 *
 */

@Embeddable
public class UserChatId implements Serializable {
    private long chatId;
    private long userId;

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
