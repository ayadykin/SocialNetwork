package com.social.network.dto.chat;

import java.io.Serializable;

/**
 * Created by Yadykin Andrii Aug 26, 2016
 *
 */

public class SendMessageDto implements Serializable {
    private String message;
    private long chatId;
    private boolean publicMessage;

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

    public boolean getPublicMessage() {
        return publicMessage;
    }

    public void setPublicMessage(boolean publicMessage) {
        this.publicMessage = publicMessage;
    }

}
