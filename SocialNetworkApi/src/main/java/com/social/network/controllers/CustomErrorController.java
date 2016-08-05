package com.social.network.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.exceptions.chat.ChatException;
import com.social.network.exceptions.friend.FriendException;
import com.social.network.exceptions.user.UserException;

@ControllerAdvice
public class CustomErrorController {

    private final static Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    private static final String ERROR_VIEW_NAME = "error/general";

    @ExceptionHandler(Exception.class)
    public String toErrorViewPage(Exception ex) {
        logger.error("-> redirect to error page with exception:", ex);
        return ERROR_VIEW_NAME;
    }

    @ResponseBody
    @ExceptionHandler({ ChatException.class, FriendException.class, UserException.class })
    public String apiError(Exception e) {
        logger.error(" -> apiError : {}", e.getMessage());
        return "{'error' : " + e.getMessage() + "}";
    }
}