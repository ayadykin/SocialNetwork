package com.social.network.exceptions.group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by andrii.perylo on 5/24/2016.
 */
public class GroupAdminException extends GroupPermissionExceptions {

    public GroupAdminException(String message) {
        super(message);
    }
}
