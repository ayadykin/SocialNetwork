package com.social.network.message.domain.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Document(collection = "recipient")
public class Recipient implements Serializable {


    private boolean readed;

    private long userId;

    public Recipient() {

    }

    public Recipient(long userId) {
        this.userId = userId;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
