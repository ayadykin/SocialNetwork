package com.social.network.domain.exceptions.chat;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

public class ChatNotExistException extends RuntimeException {
    
    public ChatNotExistException(String message) {
        super(message);
    }
}
