package com.social.network.services;

import java.util.List;

import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 * Service for user's profile
 */

public interface ProfileService {

    /**
     * Update user's profile
     * 
     * @param profileDto
     * @return profileDto
     */
    
    User updateProfile(String firstName, String lastName, String street, String city, String country, String locale, boolean translate);

    /**
     * Get list of profiles searched by profile params
     * 
     * @param profileDto
     * @return lots of profiles
     */
    List<User> searchProfile(String firstName, String lastName, String city, String country);

    /**
     * Update user's password
     *
     * @param passwordDto
     * @return ProfileDto
     */
    String changePassword(String oldPassword, String newPassword, String confirmPassword);

    
}
