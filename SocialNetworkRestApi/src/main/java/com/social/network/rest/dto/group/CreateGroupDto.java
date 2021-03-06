package com.social.network.rest.dto.group;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Yadykin Andrii Aug 17, 2016
 *
 */

public class CreateGroupDto implements Serializable {

    private String groupName;
    private String[] friendsId;
    private boolean publicGroup;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String[] getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(String[] friendsId) {
        this.friendsId = friendsId;
    }

    public boolean getPublicGroup() {
        return publicGroup;
    }

    public void setPublicGroup(boolean publicGroup) {
        this.publicGroup = publicGroup;
    }

    @Override
    public String toString() {
        return "CreateGroupDto [groupName=" + groupName + ", friendsId=" + Arrays.toString(friendsId) + ", publicGroup=" + publicGroup
                + "]";
    }

}
