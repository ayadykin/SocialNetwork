package com.social.network.services;

import java.util.List;

import com.social.network.dto.PasswordDto;
import com.social.network.dto.profile.FullProfileDto;
import com.social.network.dto.profile.PublicProfileDto;
import com.social.network.dto.profile.UserProfileDto;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 * Service for user's profile
 */

public interface ProfileService {

    /**
     * Get user's profile
     * 
     * @return fullProfileDto
     */
    FullProfileDto getProfile();
    
    /**
     * Get profile by userId
     * 
     * @param userId
     * @return userProfileDto
     */
    UserProfileDto viewProfile(long userId);

    /**
     * Update user's profile
     * 
     * @param profileDto
     * @return profileDto
     */
    
    FullProfileDto updateProfile(FullProfileDto profileDto);

    /**
     * Get list of profiles searched by profile params
     * 
     * @param profileDto
     * @return lots of profiles
     */
    List<UserProfileDto> searchProfile(PublicProfileDto profileDto);

    /**
     * Update user's password
     *
     * @param passwordDto
     * @return ProfileDto
     */
    boolean changePassword(PasswordDto passwordDto);

    
}
