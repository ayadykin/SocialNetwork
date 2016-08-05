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
import com.social.network.dto.ProfileDto;
import com.social.network.services.ProfileService;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 */

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private final static Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private static final String USER_PROFILE_VIEW_NAME = "profile/userProfile";
    private static final String SEARCH_VIEW_NAME = "search/search";
    private static final String VIEW_PROFILE_VIEW_NAME = "profile/viewProfile";
    private static final String CHANGE_PASSWORD_VIEW_NAME = "profile/changePassword";
    private static final String PROFILE_LIST_ATTRIBUTE = "profile_list";

    @Autowired
    private ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public String getProfile(Model model, @RequestParam(value = "newUser", required = false) String newUser) {
        model.addAttribute(profileService.getProfile());
        if (newUser != null) {
            model.addAttribute("msg", "You've been signed in successfully! Please fill and save your profile.");
        }
        return USER_PROFILE_VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveProfile(@ModelAttribute ProfileDto profileDto) {
        profileService.updateProfile(profileDto);
        return USER_PROFILE_VIEW_NAME;
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String initChangePassword(Model model) {
        model.addAttribute(new PasswordDto());
        return CHANGE_PASSWORD_VIEW_NAME;
    }

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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String initSearchProfile(Model model) {
        return SEARCH_VIEW_NAME;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView searchProfile(@ModelAttribute ProfileDto profileDto) {
        ModelAndView mav = new ModelAndView(SEARCH_VIEW_NAME);
        mav.addObject(PROFILE_LIST_ATTRIBUTE, profileService.searchProfile(profileDto));
        return mav;
    }

    @RequestMapping(value = "/view/{userId}", method = RequestMethod.GET)
    public ModelAndView viewProfile(@PathVariable("userId") long userId) {
        ModelAndView mav = new ModelAndView(VIEW_PROFILE_VIEW_NAME);
        mav.addObject("user", profileService.viewProfile(userId));
        return mav;
    }
}
