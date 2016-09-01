package com.social.network.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.social.network.domain.model.Account;
import com.social.network.domain.model.Profile;
import com.social.network.domain.model.User;
import com.social.network.dto.SignupFormDto;
import com.social.network.services.AuthService;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

    private final static Logger logger = LoggerFactory.getLogger(SignupController.class);
    private static final String SIGNUP_VIEW_NAME = "signup/signup";

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute(new SignupFormDto());
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String signup(Model model, @Valid @ModelAttribute SignupFormDto signupFormDto, BindingResult result) {
        if (result.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }
        User user = new User("", "", "", new Profile());
        Account account = new Account(signupFormDto.getEmail(), signupFormDto.getPassword(), "ROLE_USER", user);
        if (!authService.signup(account)) {
            model.addAttribute("exception", "User with this email already exist!");
            return SIGNUP_VIEW_NAME;
        }
        authService.signin(account);
        return "redirect:/profile?newUser";
    }
}
