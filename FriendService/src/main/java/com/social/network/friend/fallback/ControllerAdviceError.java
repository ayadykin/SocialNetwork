package com.social.network.friend.fallback;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Yadykin Andrii Nov 30, 2016
 *
 */

@Log4j2
@ControllerAdvice
public class ControllerAdviceError {

    @ResponseBody
    @ExceptionHandler({Exception.class })
    public Map<String, String> apiError(Exception e) {
        log.error(" -> apiError : {}", e.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return error;
    }
}
