package com.social.network.dto;

import java.util.Set;

/**
 * Created by andrii.perylo on 5/19/2016.
 */
public class GroupDto {

    private String name;
    private long chatId;
    private long groupId;
    private Set<GroupUserDto> users;
    private boolean groupAdmin;
    private boolean hidden;

    public GroupDto() {
    }

    public GroupDto(String name, long group, long chat, Set<GroupUserDto> users, boolean groupAdmin, boolean hidden) {
        this.name = name;
        this.chatId = chat;
        this.groupId = group;
        this.users = users;
        this.groupAdmin = groupAdmin;
        this.hidden = hidden;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public Set<GroupUserDto> getUsers() {
        return users;
    }

    public void addUser(GroupUserDto user) {
        this.users.add(user);
    }

    public void setUsers(Set<GroupUserDto> users) {
        this.users = users;
    }

    public boolean isGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(boolean groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "GroupDto [ name=" + name + ", chatId=" + chatId + ", users=" + users + ", groupAdmin=" + groupAdmin
                + ", hidden=" + hidden + "]";
    }

}
