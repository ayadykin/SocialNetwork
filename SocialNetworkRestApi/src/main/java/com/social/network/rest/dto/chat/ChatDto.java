package com.social.network.rest.dto.chat;

/**
 * Created by Yadykin Andrii May 18, 2016
 *
 */

public class ChatDto extends ChatIdDto{

    private String name;
    private boolean hidden;

    public ChatDto() {

    }

    public ChatDto(long chatId, String name, boolean hidden) {
        super(chatId);
        this.name = name;
        this.hidden = hidden;
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
