package com.social.network.dto;

/**
 * Created by Yadykin Andrii May 18, 2016
 *
 */

public class ChatDto {

    private long chatId;
    private String name;
    private boolean hidden;

    public ChatDto() {

    }

    public ChatDto(long chatId, String name, boolean hidden) {
        this.chatId = chatId;
        this.name = name;
        this.hidden = hidden;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

}
