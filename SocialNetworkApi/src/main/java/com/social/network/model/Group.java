package com.social.network.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQuery;

import com.social.network.utils.Constants;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Entity
@Table(name = "groups")
@PrimaryKeyJoinColumn(name = "chatId")
@NamedQuery(name = Constants.FIND_GROUP_BY_OWNER, query = "select g from Group g join g.users u where u = :user")
public class Group extends Chat implements Serializable {

    @NotNull
    private String groupName;

    @NotNull
    private long adminId;

    public Group() {
    }

    public Group(String groupName, long adminId, List<User> users) {
        super(users);
        this.groupName = groupName;
        this.adminId = adminId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

}
