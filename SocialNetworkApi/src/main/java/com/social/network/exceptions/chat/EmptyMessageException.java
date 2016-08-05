package com.social.network.exceptions.chat;

/**
 * Created by Yadykin Andrii Jun 2, 2016
 *
 */

public class EmptyMessageException extends RuntimeException {
    public EmptyMessageException(String message) {
        super(message);
    }
}
