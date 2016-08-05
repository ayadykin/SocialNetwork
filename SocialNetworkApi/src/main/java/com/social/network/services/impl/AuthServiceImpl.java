package com.social.network.services.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.AccountDao;
import com.social.network.model.Account;
import com.social.network.services.AuthService;

/**
 * Created by Yadykin Andrii May 16, 2016
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean signup(Account account) {
        logger.debug(" signup user email : {} ", account.getEmail());
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        //Check if user with same email already exist
        if (accountDao.findByEmail(account.getEmail()) != null) {
            logger.error(" signup user email exist : {}", account.getEmail());
            return false;
        }
        accountDao.save(account);
        return true;
    }

    @Override
    public void signin(Account account) {
        logger.debug("signin user email : {} ", account.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUser(account), null,
                Collections.singleton(createAuthority(account)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Account createUser(Account account) {
        account.setAuthorities(Collections.singleton(createAuthority(account)));
        return account;
    }

    private GrantedAuthority createAuthority(Account account) {
        return new SimpleGrantedAuthority(account.getRole());
    }
}
