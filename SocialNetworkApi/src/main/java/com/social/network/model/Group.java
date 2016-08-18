package com.social.network.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQuery;

import com.social.network.model.labels.HiddenLabel;
import com.social.network.utils.Constants;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Entity
@Table(name = "groups")
@NamedQuery(name = Constants.FIND_GROUP_BY_OWNER, 
query = "select g from Group g join g.chat.users u where u = :user order by groupId")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;
    
    @Embedded
    private HiddenLabel hidden;
    
    @OneToOne
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
        this.hidden = new HiddenLabel();
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

    public boolean isHidden() {
        return chat.getHidden();
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

    public boolean getHidden() {
        return hidden.isHidden();
    }

}
