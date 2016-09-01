package com.social.network.dto.profile;

import com.social.network.domain.model.enums.FriendStatus;

/**
 * Created by Yadykin Andrii May 18, 2016
 *
 */

public class UserProfileDto extends PublicProfileDto {

    private long userId;
    private FriendStatus friendStatus;

    public UserProfileDto() {

    }

    public UserProfileDto(long userId, String firstName, String lastName, String street, String city, String country) {
        super(firstName, lastName, street, city, country);
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

    @Override
    public String toString() {
        return "UserDto [userId=" + userId + ", friendStatus=" + friendStatus + "]";
    }

}
