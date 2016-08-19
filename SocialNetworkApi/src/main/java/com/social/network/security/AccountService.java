package com.social.network.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.AccountDao;
import com.social.network.domain.model.Account;
import com.social.network.exceptions.user.UserNotExistException;

/**
 * Created by Yadykin Andrii May 13, 2016
 *
 */
public class AccountService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        logger.debug("loadUserByUsername : username = {}", username);
        Account account = accountDao.findByEmail(username);
        if (account == null) {
            throw new UserNotExistException("user not found");
        }
        account.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(account.getRole())));
        logger.debug("loadUserByUsername : account = {}", account);

        return account;
    }
}
