package com.social.network.dto.chat;

import java.io.Serializable;

/**
 * Created by Yadykin Andrii Aug 26, 2016
 *
 */

public class EditMessageDto implements Serializable {
    private String message;
    private long messageId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

}
