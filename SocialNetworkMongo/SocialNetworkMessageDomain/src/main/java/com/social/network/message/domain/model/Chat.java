package com.social.network.message.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Document
public class Chat {

    @Id
    private long chatId;

    private List<MongoMessage> messages = new ArrayList<>();

    public Chat() {

    }

    public Chat(long chatId) {
        this.chatId = chatId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public List<MongoMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<MongoMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(MongoMessage message) {
        this.messages.add(message);
    }

    @Override
    public String toString() {
        return "Chat [chatId=" + chatId + ", messages=" + messages + "]";
    }

}
