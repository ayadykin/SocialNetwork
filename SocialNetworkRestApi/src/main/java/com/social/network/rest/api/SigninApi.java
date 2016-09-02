package com.social.network.rest.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.utils.RestResponse;

/**
 * Created by Yadykin Andrii Aug 4, 2016
 *
 */

@RestController
@RequestMapping(value = "/signin")
public class SigninApi {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getToken() {
        return new RestResponse().convert(() -> true);
    }

}
