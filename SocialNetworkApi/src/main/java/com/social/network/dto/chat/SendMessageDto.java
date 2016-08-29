package com.social.network.dto.chat;

import java.io.Serializable;

/**
 * Created by Yadykin Andrii Aug 26, 2016
 *
 */

public class SendMessageDto implements Serializable {
    private String message;
    private long chatId;
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

}
