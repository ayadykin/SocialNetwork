package com.social.network.rest.utils;

/**
 * Created by Yadykin Andrii Sep 2, 2016
 *
 */

public class Constants {

	/**
	 * Friend path
	 */
	public static final String FRIEND_PATH = "/friend";
	public static final String INVITE_FRIEND_PATH = "/inviteFriend";
	public static final String ACCEPT_INVITATION_PATH = "/acceptInvitation";
	public static final String DECLINE_INVITATION_PATH = "/declineInvitation";

	public static final String USER_PARAM = "/{userId}";

	/**
	 * Group path
	 */
	public static final String GROUP_PATH = "/group";
	public static final String GROUP_PARAM = "/{groupId}";
	public static final String LEAVE_GROUP = "/leave_group";
	public static final String DELETE_USER = "/delete_user";
	public static final String ADD_USER = "/add_user";
	
	/**
	 * Chat path
	 */
	
	public static final String CHAT_PATH = "/chat";
	public static final String CHAT_PARAM = "/{chatId}";
}
