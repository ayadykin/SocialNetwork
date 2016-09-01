package com.social.network.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.dto.profile.FullProfileDto;
import com.social.network.dto.profile.PublicProfileDto;
import com.social.network.dto.profile.UserProfileDto;
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
    public FullProfileDto getProfile() {
        return profileService.getProfile();
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserProfileDto viewProfile(@PathVariable("userId") long userId) {
        return profileService.viewProfile(userId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public FullProfileDto saveProfile(@RequestBody FullProfileDto profileDto) {
        return profileService.updateProfile(profileDto);
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<UserProfileDto> searchProfile(@RequestBody PublicProfileDto profileDto) {
        return profileService.searchProfile(profileDto);

    }

}
