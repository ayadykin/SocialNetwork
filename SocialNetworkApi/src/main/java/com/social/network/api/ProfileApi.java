package com.social.network.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.dto.ProfileDto;
import com.social.network.services.ProfileService;

/**
 * Created by Yadykin Andrii Aug 23, 2016
 *
 */

@RestController
@RequestMapping(value = "/profile")
public class ProfileApi {
    @Autowired
    private ProfileService profileService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ProfileDto getProfile() {
        return profileService.getProfile();
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ProfileDto viewProfile(@PathVariable("userId") long userId) {
        return profileService.viewProfile(userId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ProfileDto saveProfile(@RequestBody ProfileDto profileDto) {
        return profileService.updateProfile(profileDto);
    }

}
