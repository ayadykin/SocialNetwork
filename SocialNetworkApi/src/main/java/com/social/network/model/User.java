package com.social.network.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Created by Yadykin Andrii May 11, 2016
 *
 */

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @OrderBy("creation")
    @ManyToMany(mappedBy = "users")
    private Set<Chat> userChats = new HashSet<>();

    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;

    public User() {

    }

    public User(Profile profile) {
        this.profile = profile;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Set<Chat> getUserChats() {
        return userChats;
    }

    public void addChat(Chat chat) {
        this.userChats.add(chat);
    }

    public void removeChat(Chat chat) {
        this.userChats.remove(chat);
    }

    public void setUserChats(Set<Chat> userChats) {
        this.userChats = userChats;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUserFullName() {
        return new StringBuilder().append(profile.getFirstName()).append(" ").append(profile.getLastName()).toString();
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + "]";
    }

}
