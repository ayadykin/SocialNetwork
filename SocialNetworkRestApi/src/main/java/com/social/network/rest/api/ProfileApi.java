package com.social.network.rest.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.rest.dto.profile.FullProfileDto;
import com.social.network.rest.dto.profile.PasswordDto;
import com.social.network.rest.dto.profile.PublicProfileDto;
import com.social.network.rest.dto.profile.UserProfileDto;
import com.social.network.rest.facade.ProfileServiceFacade;

/**
 * Created by Yadykin Andrii Aug 23, 2016
 *
 */

@RestController
@RequestMapping(value = "/profile")
public class ProfileApi {

	private static final Logger logger = LoggerFactory.getLogger(ProfileApi.class);

	@Autowired
	private ProfileServiceFacade profileServiceFacade;

	@RequestMapping(method = RequestMethod.GET)
	public FullProfileDto getProfile() {
		return profileServiceFacade.getProfile();
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public UserProfileDto viewProfile(@PathVariable("userId") long userId) {
		return profileServiceFacade.viewProfile(userId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public FullProfileDto saveProfile(@RequestBody FullProfileDto profileDto) {
		return profileServiceFacade.updateProfile(profileDto);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public List<UserProfileDto> searchProfile(@RequestBody PublicProfileDto profileDto) {
		return profileServiceFacade.searchProfile(profileDto);

	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String changePassword(@RequestBody PasswordDto passwordDto) {
		return profileServiceFacade.changePassword(passwordDto);
	}
}
