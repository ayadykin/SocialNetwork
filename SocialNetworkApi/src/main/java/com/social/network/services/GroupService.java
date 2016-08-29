package com.social.network.services;

import java.util.List;

import com.social.network.core.GroupModel;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii May 12, 2016
 */

public interface GroupService {

    /**
     * This method is used to get logged user groups
     *
     * @return List<GroupsDto>
     */
    List<Group> getGroups();

    /**
     * This method is used to get group for user
     *
     * @param groupId
     * @return Groups
     */
    Group getGroup(long groupId);

    /**
     * This method is used to create group which called from controller
     *
     * @param name
     * @param userList
     * @return Groups
     */
    Group createGroup(String name, String[] userList);

    /**
     * This method is used to add user to group
     *
     * @param groupId
     * @param invitedUserId
     * @return information message
     */
    GroupModel addUserToGroup(long groupId, long invitedUserId);

    /**
     * This method is used to delete user from group
     *
     * @param groupId
     * @param removedUserId
     * @return information message
     */
    GroupModel deleteUserFromGroup(long groupId, long removedUserId);

    /**
     * This method is used to leave group. Admin can't leave group
     *
     * @param groupId
     * @return information message
     */
    Message leaveGroup(long groupId);

    /**
     * This method is used to delete group
     *
     * @param groupId
     * @return information messag
     */
    GroupModel deleteGroup(long groupId);

    /**
     * This method is used to get user friends that isn't in group
     *
     * @param groupId
     * @return List<FriendDto>
     */
    List<User> getFriendsNotInGroup(long groupId);
}
