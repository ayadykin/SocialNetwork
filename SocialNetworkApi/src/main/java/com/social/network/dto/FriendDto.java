package com.social.network.dto;

import com.social.network.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii May 20, 2016
 *
 */

public class FriendDto {

    private String name;
    private FriendStatus status;
    private long chatId;

    public FriendDto() {

    }

    public FriendDto(String name, FriendStatus status, long chatId) {
        this.name = name;
        this.status = status;
        this.chatId = chatId;
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

}
