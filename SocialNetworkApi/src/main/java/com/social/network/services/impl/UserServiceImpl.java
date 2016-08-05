package com.social.network.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.AccountDao;
import com.social.network.dao.UsersDao;
import com.social.network.model.Account;
import com.social.network.model.User;
import com.social.network.services.UserService;
import com.social.network.validation.DaoValidation;

/**
 * Created by Yadykin Andrii May 16, 2016
 *
 */

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UsersDao userDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long userId) {
        logger.debug(" getUserById userId : {} ", userId);
        return DaoValidation.userExistValidation(userDao, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User getLoggedUserEntity() {
        logger.debug(" getLoggedUserEntity ");

        return userDao.get(getLoggedAccount().getUser().getUserId());
    }

    @Override
    public long getLoggedUserId() {
        logger.debug(" getLoggedUserId ");
        return getLoggedAccount().getUser().getUserId();
    }

    private Account getLoggedAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    @Transactional(readOnly = true)
    public Account getLoggedAccountEntity() {
        logger.debug(" getLoggedAccountEntity ");
        return accountDao.merge(getLoggedAccount());
    }

}
