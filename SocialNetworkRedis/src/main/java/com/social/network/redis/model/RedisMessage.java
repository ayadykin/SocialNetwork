package com.social.network.redis.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Yadykin Andrii Sep 2, 2016
 *
 */

@Getter
@Setter
@ToString
public class RedisMessage implements Serializable {
    private long chatId;
    private long messageId;
    private String text;
    private Date date;
    private long ownerId;
    private String ownerName;
    private boolean hidden;
    private Set<Long> recipientsList;
    // private SystemMessageStatus messageInviteStatus;

    public RedisMessage() {
    }

    public RedisMessage(String text, long chatId, String ownerName) {
        this.text = text;
        this.chatId = chatId;
        this.ownerName = ownerName;
    }

    public RedisMessage(long messageId, String text, Date date, String ownerName, long ownerId) {
        this.messageId = messageId;
        this.text = text;
        this.date = date;
        this.ownerName = ownerName;
        this.ownerId = ownerId;
    }

}
