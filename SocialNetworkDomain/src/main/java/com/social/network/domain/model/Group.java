package com.social.network.domain.model;

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

import com.social.network.domain.model.labels.HiddenLabel;
import com.social.network.domain.util.Constants;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Entity
@Table(name = "groups")
@NamedQuery(name = Constants.FIND_GROUP_BY_OWNER, query = "select g from Group g join g.chat.userChat u where u.user = :user order by groupId")
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

    @OneToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    public Group() {
    }

    public Group(Chat chat, String groupName, User admin) {
        this.chat = chat;
        this.groupName = groupName;
        this.admin = admin;
        this.hidden = new HiddenLabel();
    }

    public void hiddeGroup() {
        this.hidden.setHidden(true);
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

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public boolean getHidden() {
        return hidden.isHidden();
    }

}
