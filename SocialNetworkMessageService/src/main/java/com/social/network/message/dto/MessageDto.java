package com.social.network.message.dto;

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
    private long messageId;
    private String text;
    private long publisher;
    private Set<Long> resipientsId;
}
