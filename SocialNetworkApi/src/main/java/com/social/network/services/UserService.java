package com.social.network.services;

import com.social.network.model.Account;
import com.social.network.model.User;

/**
 * Created by Yadykin Andrii May 13, 2016
 *
 */

public interface UserService {

    /**
     * Get user by userId
     * 
     * @param userId
     * @return user entity
     */
    User getUserById(long userId);

    /**
     * Get logged user
     * 
     * @return user
     */
    User getLoggedUserEntity();

    Account getLoggedAccountEntity();

    long getLoggedUserId();

}
