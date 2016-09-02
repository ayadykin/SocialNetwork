package com.social.network.rest.dto.group;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

public class GroupUserDto {
    private long userId;
    private String fullName;
    private boolean groupAdmin;

    public GroupUserDto() {

    }

    public GroupUserDto(long userId, String fullName, boolean groupAdmin) {
        this.userId = userId;
        this.fullName = fullName;
        this.groupAdmin = groupAdmin;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(boolean groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    @Override
    public String toString() {
        return "GroupUserDto [userId=" + userId + ", fullName=" + fullName + ", groupAdmin=" + groupAdmin + "]";
    }

}
