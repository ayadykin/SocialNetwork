package com.social.network.message.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "chat")
public class Chat {

    @Id
    private long chatId;

    private boolean hidden;

    private List<MongoMessage> messages = new ArrayList<>();

    public Chat(long chatId) {
        this.chatId = chatId;
    }

    public List<MongoMessage> addMessage(MongoMessage message) {
        this.messages.add(message);
        return messages;
    }
}
