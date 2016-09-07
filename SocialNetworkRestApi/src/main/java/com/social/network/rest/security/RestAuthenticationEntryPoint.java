package com.social.network.rest.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Created by Yadykin Andrii Sep 6, 2016
 *
 */

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        logger.error(" commence error {}", authException.getMessage());
        JSONObject obj = new JSONObject();
        obj.put("error", authException.getMessage());
        obj.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(obj);
    }

}
