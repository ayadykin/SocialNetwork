package com.social.network.rest.dto;

import com.social.network.domain.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii May 20, 2016
 *
 */

public class FriendDto {

    private String name;
    private FriendStatus status;
    private long chatId;
    private long userId;

    public FriendDto() {

    }

    public FriendDto( String name, FriendStatus status, long chatId, long userId) {
        this.name = name;
        this.status = status;
        this.chatId = chatId;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

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
