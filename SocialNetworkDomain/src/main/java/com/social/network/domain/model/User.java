package com.social.network.domain.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OrderBy("friendId")
    @OneToMany(mappedBy = "user")
    private Set<Friend> friends = new LinkedHashSet<>();

    @OrderBy("chat.chatId")
    @OneToMany(mappedBy = "user")
    private Set<UserChat> userChat = new LinkedHashSet<>();

    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String locale;

    public User(String firstName, String lastName, String locale, Profile profile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.locale = locale;
        this.profile = profile;
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<UserChat> getUserChat() {
        return userChat;
    }

    public void setUserChat(Set<UserChat> userChat) {
        this.userChat = userChat;
    }

    public Set<Friend> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friend> friends) {
        this.friends = friends;
    }

    public void addFriend(Friend friend) {
        this.friends.add(friend);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUserFullName() {
        return new StringBuilder().append(firstName).append(" ").append(lastName).toString();
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", profileId=" + profile.getProfileId() + ", firstName="
                + firstName + ", lastName=" + lastName + ", locale=" + locale + "]";
    }

}
