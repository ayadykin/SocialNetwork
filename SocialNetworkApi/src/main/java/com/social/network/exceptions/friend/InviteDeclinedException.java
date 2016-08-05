package com.social.network.exceptions.friend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Yadykin Andrii May 24, 2016
 *
 */
public class InviteDeclinedException extends InviteException{

    public InviteDeclinedException(String message) {
        super(message);
    }
}

