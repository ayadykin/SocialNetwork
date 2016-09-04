package com.social.network.rest.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.social.network.domain.model.Profile;
import com.social.network.domain.model.User;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.rest.dto.profile.FullProfileDto;
import com.social.network.rest.dto.profile.PasswordDto;
import com.social.network.rest.dto.profile.PublicProfileDto;
import com.social.network.rest.dto.profile.UserProfileDto;
import com.social.network.services.ProfileService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii Sep 2, 2016
 *
 */

@Service
public class ProfileServiceFacade {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;

    
    public FullProfileDto getProfile() {
        User loggedUser = userService.getLoggedUserEntity();
        Profile profile = loggedUser.getProfile();
        return new FullProfileDto(loggedUser.getFirstName(), loggedUser.getLastName(), profile.getStreet(), profile.getCity(),
                profile.getCountry(), loggedUser.getLocale(), profile.isTranslate());
    }

    
    public UserProfileDto viewProfile(long userId) {
        User loggedUser = userService.getLoggedUserEntity();
        return cerateUserProfileDto(loggedUser);
    }

    
    public FullProfileDto updateProfile(FullProfileDto profileDto) {
        
         User user = profileService.updateProfile(profileDto.getFirstName(), profileDto.getLastName(), profileDto.getStreet(),
                profileDto.getCity(), profileDto.getCountry(), profileDto.getLocale(), profileDto.getTranslate());
         //TODO
         return profileDto;
    }

    @Transactional(readOnly = true)
    public List<UserProfileDto> searchProfile(@RequestBody PublicProfileDto profileDto) {
        List<User> users = profileService.searchProfile(profileDto.getFirstName(), profileDto.getLastName(), profileDto.getCity(),
                profileDto.getCountry());

        List<UserProfileDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(cerateUserProfileDto(user));
        }
        return usersDto;
    }

    
    public String changePassword(PasswordDto passwordDto) {
        return profileService.changePassword(passwordDto.getOldPassword(), passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
    }

    private UserProfileDto cerateUserProfileDto(User user) {

        Profile profile = user.getProfile();

        UserProfileDto userProfileDto = new UserProfileDto(user.getUserId(), user.getFirstName(), user.getLastName(), profile.getStreet(),
                profile.getCity(), profile.getCountry());
        setFriendStatus(user, userProfileDto);

        return userProfileDto;
    }

    private void setFriendStatus(User user, UserProfileDto userDto) {
        User loggedUser = userService.getLoggedUserEntity();

        if (user.getUserId() == loggedUser.getUserId()) {
            userDto.setFriendStatus(FriendStatus.YOU);
        } else {
            user.getFriends().stream().filter(f -> f.getFriend().getUserId() == loggedUser.getUserId()).findFirst().ifPresent(x -> {
                userDto.setFriendStatus(x.getFriendStatus());
            });
        }

    }
}
