package com.social.network.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii Aug 4, 2016
 *
 */

@Controller
@RequestMapping(value = "/signin")
public class SigninApi {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public SigninResponse getToken() {
        return new SigninResponse().setUserId(userService.getLoggedUserId());
    }

    private class SigninResponse {
        private long userId;

        public long getUserId() {
            return userId;
        }

        public SigninResponse setUserId(long userId) {
            this.userId = userId;
            return this;
        }

    }
}
