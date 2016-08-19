package com.social.network.core;

import com.social.network.domain.model.Group;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii Aug 18, 2016
 *
 */

public class GroupModel {
    private User invitedUser;
    private User loggedUser;
    private Group group;

    public GroupModel(User invitedUser, User loggedUser, Group group) {
        this.invitedUser = invitedUser;
        this.loggedUser = loggedUser;
        this.group = group;
    }

    public User getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(User invitedUser) {
        this.invitedUser = invitedUser;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
