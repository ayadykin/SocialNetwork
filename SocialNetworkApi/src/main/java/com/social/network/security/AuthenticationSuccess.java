package com.social.network.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.social.network.domain.model.Account;

/**
 * Created by Yadykin Andrii Aug 3, 2016
 *
 */

public class AuthenticationSuccess implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Account account = (Account) authentication.getPrincipal();
        response.getWriter().print("{\"userId\" : \"" + account.getUser().getUserId() + "\"}");
        response.getWriter().flush();
    }
}
