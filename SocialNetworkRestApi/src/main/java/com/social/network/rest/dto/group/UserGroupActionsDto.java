package com.social.network.rest.dto.group;

/**
 * Created by Yadykin Andrii Aug 30, 2016
 *
 */

public class UserGroupActionsDto {
    private long userId;
    private long groupId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

}
