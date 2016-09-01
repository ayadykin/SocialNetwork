package com.social.network.dto.chat;

/**
 * Created by Yadykin Andrii Aug 31, 2016
 *
 */

public class ChatIdDto {

    private long chatId;

    public ChatIdDto(){
        
    }
    
    public ChatIdDto(long chatId) {
        this.chatId = chatId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

}
