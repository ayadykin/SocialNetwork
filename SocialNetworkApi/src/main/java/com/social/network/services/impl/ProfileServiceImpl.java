package com.social.network.services.impl;

import java.util.ArrayList;
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
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.dto.PasswordDto;
import com.social.network.dto.profile.FullProfileDto;
import com.social.network.dto.profile.PublicProfileDto;
import com.social.network.dto.profile.UserProfileDto;
import com.social.network.exceptions.profile.PasswordMatchesException;
import com.social.network.services.ProfileService;
import com.social.network.services.UserService;
import com.social.network.validation.DaoValidation;

/**
 * Created by Yadykin Andrii May 17, 2016
 */
@Service
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
    @Transactional(readOnly = true)
    public FullProfileDto getProfile() {
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug(" -> getProfile for user = {}", loggedUser.getUserId());
        Profile profile = loggedUser.getProfile();
        return new FullProfileDto(loggedUser.getFirstName(), loggedUser.getLastName(), profile.getStreet(), profile.getCity(),
                profile.getCountry(), loggedUser.getLocale(), profile.isTranslate());
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto viewProfile(long userId) {
        logger.debug("-> viewProfile userId = {}", userId);
        User user = DaoValidation.userExistValidation(usersDao, userId);
        return cerateUserProfileDto(user);
    }

    @Override
    @Transactional
    public FullProfileDto updateProfile(FullProfileDto profileDto) {
        logger.debug(" -> updateProfile profileDto = {}", profileDto);
        User loggedUser = userService.getLoggedUserEntity();
        Profile profile = loggedUser.getProfile();

        // Fill profile
        loggedUser.setFirstName(profileDto.getFirstName());
        loggedUser.setLastName(profileDto.getLastName());
        loggedUser.setLocale(profileDto.getLocale());

        profile.setStreet(profileDto.getStreet());
        profile.setCity(profileDto.getCity());
        profile.setCountry(profileDto.getCountry());
        profile.setTranslate(profileDto.getTranslate());

        usersDao.saveOrUpdate(loggedUser);
        return profileDto;
    }

    @Override
    @Transactional
    public boolean changePassword(PasswordDto passwordDto) {
        Account loggedAccount = userService.getLoggedAccountEntity();
        logger.debug("-> changePassword for account {}", loggedAccount);

        // Check if old password match
        String oldPassword = loggedAccount.getPassword();
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), oldPassword)) {
            logger.debug("-> old password do not match: {}", passwordDto.getOldPassword());
            throw new PasswordMatchesException(" old password do not match");
        }

        // Save new password
        loggedAccount.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        accountDao.save(loggedAccount);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileDto> searchProfile(PublicProfileDto profileDto) {
        logger.debug(" searchProfile profileDto = {}", profileDto);
        List<User> users = usersDao.searchUser(profileDto.getFirstName(), profileDto.getLastName(), profileDto.getCity(),
                profileDto.getCountry());

        List<UserProfileDto> usersList = new ArrayList<>();
        for (User user : users) {
            usersList.add(cerateUserProfileDto(user));
        }
        logger.debug(" searchProfile usersList = {}", usersList);
        return usersList;
    }

    private UserProfileDto cerateUserProfileDto(User user) {

        Profile profile = user.getProfile();

        UserProfileDto userProfileDto = new UserProfileDto(user.getUserId(), user.getFirstName(), user.getLastName(), profile.getStreet(),
                profile.getCity(), profile.getCountry());
        setFriendStatus(user, userProfileDto);

        return userProfileDto;
    }

    private void setFriendStatus(User user, UserProfileDto userDto) {
        User loggedUser = userService.getLoggedUserEntity();

        if (user.getUserId() == loggedUser.getUserId()) {
            userDto.setFriendStatus(FriendStatus.YOU);
        } else {
            user.getFriends().stream().filter(f -> f.getFriend().getUserId() == loggedUser.getUserId()).findFirst().ifPresent(x -> {
                userDto.setFriendStatus(x.getFriendStatus());
            });
        }

    }
}
