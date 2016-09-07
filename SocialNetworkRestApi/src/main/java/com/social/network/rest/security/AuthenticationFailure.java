package com.social.network.rest.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Yadykin Andrii Aug 3, 2016
 *
 */

@Component
public class AuthenticationFailure implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        
        logger.error(" handle error : {}", accessDeniedException.getMessage());
        JSONObject obj = new JSONObject();
        obj.put("error", accessDeniedException.getMessage());
        obj.put("status", HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().print(obj);
    }

}
