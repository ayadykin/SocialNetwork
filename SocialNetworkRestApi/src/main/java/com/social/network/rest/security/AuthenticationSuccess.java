package com.social.network.rest.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.social.network.domain.model.Account;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii Aug 3, 2016
 *
 */

public class AuthenticationSuccess implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Account account = (Account) authentication.getPrincipal();
        User user = account.getUser();
        JSONObject obj = new JSONObject();
        obj.put("userId", user.getUserId());
        obj.put("userName", user.getUserFullName());
        obj.put("userLocale", user.getLocale());
        response.getWriter().print(obj);
        response.getWriter().flush();
    }
}
