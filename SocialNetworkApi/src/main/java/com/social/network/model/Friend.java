package com.social.network.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
@NamedQueries(value = { @NamedQuery(name = Constants.FIND_FRIEND_BY_OWNER, query = "select f from Friend f join f.user u where u = :user"),
        @NamedQuery(name = Constants.FIND_BY_FRIEND_AND_OWNER, query = "FROM Friend f where f.user = :invitee and f.friend = :inviter)") })
public class Friend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long friendId;

    @OneToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "user_friend_id")
    private User friend;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    private String friendName;

    public Friend() {

    }

    public Friend(Chat chat, FriendStatus friendStatus, User inviter, User invitee) {
        this.chat = chat;
        this.friendStatus = friendStatus;
        this.user = inviter;
        this.friend = invitee;
        this.friendName = invitee.getUserFullName();
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getFriend() {
        return friend;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FriendStatus getFriendStatus() {
        return friendStatus;
    }

    public Friend setFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
        return this;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

}
