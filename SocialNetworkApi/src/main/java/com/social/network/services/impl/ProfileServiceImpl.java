package com.social.network.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dao.AccountDao;
import com.social.network.dao.FriendDao;
import com.social.network.dao.UsersDao;
import com.social.network.dto.PasswordDto;
import com.social.network.dto.ProfileDto;
import com.social.network.dto.UserDto;
import com.social.network.model.Account;
import com.social.network.model.Friend;
import com.social.network.model.Profile;
import com.social.network.model.User;
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
    private FriendDao friendDao;
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
    public ProfileDto getProfile() {
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("ProfileService -> getProfile for user = {}", loggedUser.getUserId());
        Profile profile = loggedUser.getProfile();
        return new ProfileDto(profile.getFirstName(), profile.getLastName(), profile.getCity(), profile.getCountry(),
                profile.getLocale(), profile.isTranslate());
    }

    @Override
    @Transactional
    public ProfileDto updateProfile(ProfileDto profileDto) {
        logger.debug("ProfileService -> updateProfile profileDto = {}", profileDto);
        User loggedUser = userService.getLoggedUserEntity();
        Profile profile = loggedUser.getProfile();

        // Fill profile
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setCity(profileDto.getCity());
        profile.setCountry(profileDto.getCountry());
        profile.setLocale(profileDto.getLocale());
        profile.setTranslate(profileDto.getTranslate());
        
        usersDao.save(loggedUser);
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
            return false;
        }

        // Save new password
        loggedAccount.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        accountDao.save(loggedAccount);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> searchProfile(ProfileDto profileDto) {
        logger.debug(" searchProfile profileDto = {}", profileDto);
        List<User> users = usersDao.searchUser(profileDto.getFirstName(), profileDto.getLastName(),
                profileDto.getCity(), profileDto.getCountry());
        List<UserDto> usersList = new ArrayList<>();

        User loggedUser = userService.getLoggedUserEntity();
        List<Friend> friends = friendDao.findByOwner(loggedUser);
        // Fill UserDto list
        for (User user : users) {
            UserDto userDto = cerateUserDto(user.getProfile(), user.getUserId());
            setFriendStatus(user, loggedUser, friends, userDto);
            usersList.add(userDto);
        }
        return usersList;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto viewProfile(long userId) {
        logger.debug("-> viewProfile userId = {}", userId);
        User loggedUser = userService.getLoggedUserEntity();
        List<Friend> friends = friendDao.findByOwner(loggedUser);

        User user = DaoValidation.userExistValidation(usersDao, userId);
        UserDto userDto = cerateUserDto(user.getProfile(), user.getUserId());

        setFriendStatus(user, loggedUser, friends, userDto);
        return userDto;
    }

    private UserDto cerateUserDto(Profile profile, long userId) {
        return new UserDto(profile.getFirstName(), profile.getLastName(), profile.getCity(), profile.getCountry(),
                profile.getLocale(), userId);
    }

    private void setFriendStatus(User user, User loggedUser, List<Friend> friends, UserDto userDto) {

        // If this is your user id
        if (user.getUserId() == loggedUser.getUserId()) {
            userDto.setYourProfile(true);
        } else {
            for (Friend myFriend : friends) {
                /*if (myFriend.getFriend().getUserId() == user.getUserId()) {
                    userDto.setFriendStatus(myFriend.getFriendStatus());
                }*/
            }
        }
    }
}
