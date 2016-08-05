package com.social.network.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Created by Yadykin Andrii Aug 3, 2016
 *
 */

public class AuthenticationFailure implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException, AuthenticationException {
        try {
            response.getWriter().print("{\"login\": \"FAILURE\"}");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
