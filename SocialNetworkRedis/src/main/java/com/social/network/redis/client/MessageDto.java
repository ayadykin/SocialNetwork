package com.social.network.redis.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Yadykin Andrii Nov 29, 2016
 *
 */

@Getter
@Setter
@ToString
public class MessageDto implements Serializable {
    private long chatId;
    private String text;
    private long publisher;
    private Set<Long> resipientsId;
}
