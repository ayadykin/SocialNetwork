package com.social.network.services;

import java.util.List;

import com.social.network.dto.PasswordDto;
import com.social.network.dto.ProfileDto;
import com.social.network.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 * Service for user's profile
 */

public interface ProfileService {

    /**
     * Get user's profile
     * 
     * @return profileDto
     */
    ProfileDto getProfile();

    /**
     * Update user's profile
     * 
     * @param profileDto
     * @return profileDto
     */
    ProfileDto updateProfile(ProfileDto profileDto);

    /**
     * Get list of profiles searched by profile params
     * 
     * @param profileDto
     * @return lots of profiles
     */
    List<UserDto> searchProfile(ProfileDto profileDto);

    /**
     * Update user's password
     *
     * @param passwordDto
     * @return ProfileDto
     */
    boolean changePassword(PasswordDto passwordDto);

    UserDto viewProfile(long userId);
}
