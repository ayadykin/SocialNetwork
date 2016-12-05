package com.social.network.message.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
@Document(collection = "message")
public class MongoMessage {

    @Id
    private long messageId;

    private Date createDate;

    private String text;

    private long publisherId;

    private boolean hidden;

    private Set<Recipient> recipient = new HashSet<>();

    public MongoMessage(long messageId, String text, long publisherId, Set<Recipient> recipient) {
        this.messageId = messageId;
        this.createDate = new Date();
        this.text = text;
        this.publisherId = publisherId;
        this.recipient = recipient;
    }

}
