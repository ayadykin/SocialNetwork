package com.social.network.domain.util;

/**
 * Created by Yadykin Andrii Aug 19, 2016
 *
 */

public class Constants {
    /**
     * Named queries
     * 
     */
    public static final String FIND_ACCOUNT_BY_EMAIL = "Account.findByEmail";
    public static final String FIND_FRIEND_BY_OWNER = "Friend.findByOwner";
    public static final String FIND_GROUP_BY_OWNER = "Group.findByOwner";
    public static final String FIND_BY_CHAT_AND_USER = "UserChat.findByChatAndUser";
    public static final String FIND_BY_FRIEND_AND_USER = "UserFriend.findByFriendAndUser";
    public static final String FIND_BY_GROUP_AND_USER = "UserGroup.findByGroupAndUser";
    public static final String REMOVE_GROUP_AND_USER = "UserGroup.removeGroupAndUser";
    public static final String REMOVE_CHAT_AND_USER = "UserChat.removeChatAndUser";
    public static final String FIND_BY_FRIEND_AND_OWNER = "Friend.findByFriendAndOwner";
    public static final String FIND_FRIENDS_NOT_IN_GROUP = "Friend.findFriendsNotInGroup";
    public static final String FIND_SYSTEM_MESSAGE_BY_CHAT = "SystemMessage.findByChat";
    public static final String FIND_RECIPIENT_BY_MESSAGE = "Recipient.findByMessage";
}
