package com.social.network.exceptions.group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by andrii.perylo on 5/18/2016.
 */
public class GroupPermissionExceptions extends RuntimeException {

    public GroupPermissionExceptions(String message) {
        super(message);
    }
}
