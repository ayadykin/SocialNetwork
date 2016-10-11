package com.social.network.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.AccountDao;
import com.social.network.domain.dao.UsersDao;
import com.social.network.domain.model.Account;
import com.social.network.domain.model.Profile;
import com.social.network.domain.model.User;
import com.social.network.exceptions.profile.PasswordMatchesException;
import com.social.network.services.ProfileService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii May 17, 2016
 */
@Service
@Transactional(value="hibernateTx")
public class ProfileServiceImpl implements ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User updateProfile(String firstName, String lastName, String street, String city, String country, String locale,
            boolean translate) {
        logger.debug(" -> updateProfile ");
        User loggedUser = userService.getLoggedUserEntity();
        Profile profile = loggedUser.getProfile();

        // Fill profile
        loggedUser.setFirstName(firstName);
        loggedUser.setLastName(lastName);
        loggedUser.setLocale(locale);

        profile.setStreet(street);
        profile.setCity(city);
        profile.setCountry(country);
        profile.setTranslate(translate);

        usersDao.saveOrUpdate(loggedUser);
        return loggedUser;
    }

    @Override
    public String changePassword(String oldPassword, String newPassword, String confirmPassword) {
        Account loggedAccount = userService.getLoggedAccountEntity();
        logger.debug("-> changePassword for account {}", loggedAccount);

        // Check if old password match
        String oldAccPassword = loggedAccount.getPassword();
        if (!passwordEncoder.matches(oldPassword, oldAccPassword)) {
            logger.error("-> old password do not match");
            throw new PasswordMatchesException(" old password do not match");
        }

        // Save new password
        loggedAccount.setPassword(passwordEncoder.encode(newPassword));
        accountDao.save(loggedAccount);
        return "result";
    }

    @Override
    @Transactional(value="hibernateTx", readOnly = true)
    public List<User> searchProfile(String firstName, String lastName, String city, String country) {
        logger.debug(" searchProfile firstName = {}, lastName = {}, city = {}, country = {}", firstName, lastName, city, country);
        return usersDao.searchUser(firstName, lastName, city, country);
    }

}
