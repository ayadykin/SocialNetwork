package com.social.network.neo4j.exeptions;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Yadykin Andrii Nov 30, 2016
 *
 */

@Slf4j
@ControllerAdvice
public class ControllerAdviceError {

    @ResponseBody
    @ExceptionHandler({ InviteException.class })
    public Map<String, String> apiError(Exception e) {
        log.error(" -> apiError : {}", e.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return error;
    }
}
