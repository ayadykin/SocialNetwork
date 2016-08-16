package com.social.network.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.utils.RestResponse;

/**
 * Created by Yadykin Andrii Aug 4, 2016
 *
 */

@Controller
@RequestMapping(value = "/signin")
public class SigninApi {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getToken() {
        return new RestResponse().convert(() -> true);
    }
}
