package com.social.network.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.social.network.dto.PasswordDto;
import com.social.network.dto.profile.FullProfileDto;
import com.social.network.services.ProfileService;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 */

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private final static Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private static final String CHANGE_PASSWORD_VIEW_NAME = "profile/changePassword";

    @Autowired
    private ProfileService profileService;


    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String changePassword(Model model, @Valid @ModelAttribute PasswordDto passwordDto, BindingResult result) {
        if (result.hasErrors()) {
            return CHANGE_PASSWORD_VIEW_NAME;
        }
        if (!profileService.changePassword(passwordDto)) {
            model.addAttribute("status", false);
            return CHANGE_PASSWORD_VIEW_NAME;
        }
        model.addAttribute("status", true);
        return CHANGE_PASSWORD_VIEW_NAME;
    }

}
