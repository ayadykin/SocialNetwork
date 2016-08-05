package com.social.network.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.social.network.model.enums.FriendStatus;
import com.social.network.utils.Constants;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Entity
@DynamicUpdate(value = true)
@PrimaryKeyJoinColumn(name = "chatId")
@NamedQueries(value = { @NamedQuery(name = Constants.FIND_FRIEND_BY_OWNER, query = "select f from Friend f join f.users u where u = :user"),
        @NamedQuery(name = Constants.FIND_BY_FRIEND_AND_OWNER, query = "select f from Friend f join f.users u  where u = :invitee and f.inviter = :inviter") })
public class Friend extends Chat implements Serializable {

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    @NotNull
    @OneToOne
    private User inviter;

    @Transient
    private String friendName;

    public Friend() {

    }

    public Friend(FriendStatus friendStatus, User inviter, User invitee) {
        super(Arrays.asList(inviter, invitee));
        this.friendStatus = friendStatus;
        this.inviter = inviter;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public FriendStatus getFriendStatus() {
        return friendStatus;
    }

    public Friend setFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
        return this;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "Friend [chatId=" + getChatId() + ", friendStatus=" + friendStatus + ", inviterId=" + inviter.getUserId() + "]";
    }

}
