package com.social.network.message.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "recipient")
public class Recipient implements Serializable {

    private boolean readed;

    private long userId;

    public Recipient(long userId) {
        this.userId = userId;
    }

}
