package com.social.network.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    private static final String HOME_VIEW = "home/homeSignedIn";
    private static final String HOME_VIEW_ERROR = "home/homeNotSignedIn";

    @RequestMapping(method = RequestMethod.GET)
    public String index(Principal principal) {
        return principal != null ? HOME_VIEW : HOME_VIEW_ERROR;
    }
}
