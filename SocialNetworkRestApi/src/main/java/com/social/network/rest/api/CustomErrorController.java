package com.social.network.rest.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.exceptions.chat.ChatException;
import com.social.network.exceptions.friend.FriendException;
import com.social.network.exceptions.group.GroupException;
import com.social.network.exceptions.profile.ProfileException;
import com.social.network.exceptions.user.UserException;

/**
 * @author andrii.yadykin
 *
 */
@ControllerAdvice
public class CustomErrorController {

    private final static Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @ResponseBody
    @ExceptionHandler({ Exception.class, ChatException.class, FriendException.class, UserException.class, GroupException.class,
            ProfileException.class })
    public Map<String, String> apiError(Exception e) {
        logger.error(" -> apiError : {}", e);
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return error;
    }
}