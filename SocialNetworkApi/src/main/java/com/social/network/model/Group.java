package com.social.network.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@NamedQuery(name = Constants.FIND_GROUP_BY_OWNER, query = "select g from Group g join g.chat.users u where u = :user")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @NotNull
    private String groupName;

    @NotNull
    private long adminId;

    public Group() {
    }

    public Group(Chat chat, String groupName, long adminId) {
        this.chat = chat;
        this.groupName = groupName;
        this.adminId = adminId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Chat getChat() {
        return chat;
    }
    
    public long getChatId() {
        return chat.getChatId();
    }
    
    public void setChat(Chat chat) {
        this.chat = chat;
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
