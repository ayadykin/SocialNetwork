package com.social.network.security;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.AccountDao;
import com.social.network.domain.model.Account;
import com.social.network.exceptions.user.UserNotExistException;

/**
 * Created by Yadykin Andrii May 13, 2016
 *
 */
@Service
public class AccountService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(value="hibernateTx", readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername : username = {}", email);
        Account account = accountDao.findByEmail(email);
        if (Objects.isNull(account)) {
            logger.error("error loadUserByUsername email : {}", email);
            throw new UserNotExistException("user not found");
        }
        account.setAuthorities(AuthorityUtils.createAuthorityList(account.getRoleName()));

        return account;
    }
}
