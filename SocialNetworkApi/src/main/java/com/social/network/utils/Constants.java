package com.social.network.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Yadykin Andrii May 24, 2016
 *
 */

public class Constants {

    public static final String FIND_ACCOUNT_BY_EMAIL = "Account.findByEmail";
    public static final String FIND_FRIEND_BY_OWNER = "Friend.findByOwner";
    public static final String FIND_GROUP_BY_OWNER = "Group.findByOwner";
    public static final String FIND_BY_CHAT_AND_USER = "UserChat.findByChatAndUser";
    public static final String FIND_BY_GROUP_AND_USER = "UserGroup.findByGroupAndUser";
    public static final String REMOVE_GROUP_AND_USER = "UserGroup.removeGroupAndUser";
    public static final String REMOVE_CHAT_AND_USER = "UserChat.removeChatAndUser";
    public static final String FIND_BY_FRIEND_AND_OWNER = "Friend.findByFriendAndOwner";
    public static final String FIND_FRIENDS_NOT_IN_GROUP = "Friend.findFriendsNotInGroup";
    public static final String FIND_SYSTEM_MESSAGE_BY_CHAT = "SystemMessage.findByChat";
    public static final String FIND_RECIPIENT_BY_MESSAGE = "Recipient.findByMessage";
    public static final DateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter jodaFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    public static final String SUCCESS_RESPONSE = "Success";
    public static final String ERROR_RESPONSE = "Error";
    
    
    /**
     * Validation constant
     */
    public static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    public static final String EMAIL_MESSAGE = "{email.message}";
    public static final String MIN_PASSWORD_SIZE = "{min.password.size}";
    public static final String SECURE_PASSWORD_ERROR = "{secure.password.error}";
    public static final String SECURE_PASSWORD_NO_DIGITS ="{secure.password.no.digits}";
    public static final String SECURE_PASSWORD_LOWER_CASE="{secure.password.lower.case}";
    public static final String SECURE_PASWWORD_UPPER_CASE="{secure.password.upper.case}";
    public static final String SECURE_PASSWORD_SYMBOLS="{secure.password.symbols}";
    public static final String SECURE_PASSWORD_SPACES="{secure.password.spaces}";
    
    
    /**
     * Friend message constant
     */
    public final static String INVITATION_MESSAGE = "invitation.message";
    public final static String ACCEPT_INVITATION_MESSAGE = "accept.invitation.message";
    public final static String DECLINE_INVITATION_MESSAGE = "decline.invitation.message";
    
    /**
     * Group message constant
     */
    public final static String CREATE_GROUP_MESSAGE = "crearte.group.message";
    public final static String ADD_USER_TO_GROUP_MESSAGE = "group.added.message";
    public final static String DELETE_USER_FROM_GROUP_MESSAGE = "delete.from.group.message";
    public final static String LEAVE_GROUP_MESSAGE = "leave.group.message";
    public final static String DELETE_GROUP_MESSAGE = "delete.group.message";
}
