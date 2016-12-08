package com.social.network.auth.services;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Yadykin Andrii Aug 3, 2016
 *
 */

@Log4j2
@Component
public class AuthenticationFailure implements AccessDeniedHandler {   

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        
        log.error(" handle error : {}", accessDeniedException.getMessage());
        JSONObject obj = new JSONObject();
        obj.put("error", accessDeniedException.getMessage());
        obj.put("status", HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().print(obj);
    }

}
