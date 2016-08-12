package com.social.network.dto;

import java.util.Locale;

import com.social.network.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii May 18, 2016
 *
 */

public class UserDto extends ProfileDto {

    private long userId;
    private FriendStatus friendStatus;
    private boolean yourProfile;

    public UserDto() {

    }

    public UserDto(String firstName, String lastName, String city, String country, Locale locale, long userId) {
        super(firstName, lastName, city, country, locale, false);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public FriendStatus getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }

    public boolean isYourProfile() {
        return yourProfile;
    }

    public void setYourProfile(boolean yourProfile) {
        this.yourProfile = yourProfile;
    }

    @Override
    public String toString() {
        return "UserDto [userId=" + userId + ", friendStatus=" + friendStatus + ", yourProfile=" + yourProfile + "]";
    }

}
