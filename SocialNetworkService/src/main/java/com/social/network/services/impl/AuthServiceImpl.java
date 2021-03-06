package com.social.network.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.AccountDao;
import com.social.network.domain.model.Account;
import com.social.network.domain.model.User;
import com.social.network.services.AuthService;
import com.social.network.services.Neo4jService;

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
    @Autowired
	private Neo4jService neo4jService;
    
    @Override
    @Transactional(value="hibernateTx")
    public boolean signup(Account account) {
        logger.debug(" signup user email : {} ", account.getEmail());
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Check if user with same email already exist
        if (accountDao.findByEmail(account.getEmail()) != null) {
            logger.error(" signup user email exist : {}", account.getEmail());
            return false;
        }
        accountDao.save(account);
        User user = account.getUser();
        neo4jService.save(user.getUserId(), user.getUserFullName());
        return true;
    }

    @Override
    public void signin(Account account) {
        logger.debug("signin user email : {} ", account.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUser(account), null,
                AuthorityUtils.createAuthorityList(account.getRoleName()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Account createUser(Account account) {
        account.setAuthorities(AuthorityUtils.createAuthorityList(account.getRoleName()));
        return account;
    }
}
