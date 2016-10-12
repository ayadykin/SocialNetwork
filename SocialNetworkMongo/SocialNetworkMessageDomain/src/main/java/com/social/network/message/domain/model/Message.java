package com.social.network.message.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Document(collection = "message")
public class Message {

    @Id
    private long id;
    
    private String text;
}

