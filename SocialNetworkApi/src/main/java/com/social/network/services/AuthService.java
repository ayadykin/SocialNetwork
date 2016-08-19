package com.social.network.services;

import com.social.network.domain.model.Account;

/**
 * Created by Yadykin Andrii May 16, 2016
 *
 */

public interface AuthService {

    /**
     * Sign up new user
     * 
     * @param account
     * @return boolean
     */
    boolean signup(Account account);

    /**
     * Sign in user
     * 
     * @param account
     */
    void signin(Account account);

}
