package com.social.network.services.impl;

import static com.social.network.utils.Constants.ADD_USER_TO_GROUP_MESSAGE;
import static com.social.network.utils.Constants.CREATE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_USER_FROM_GROUP_MESSAGE;
import static com.social.network.utils.Constants.LEAVE_GROUP_MESSAGE;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.text.MessageTextBuilder;
import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.GroupDao;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.exceptions.group.DeleteGroupException;
import com.social.network.exceptions.group.GroupAdminException;
import com.social.network.exceptions.group.GroupPermissionExceptions;
import com.social.network.services.FriendService;
import com.social.network.services.GroupService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.services.UserService;
import com.social.network.validation.DaoValidation;

/**
 * Created by Yadykin Andrii on 5/17/2016.
 */
@Service
public class GroupServiceImpl implements GroupService {

	private static final Logger logger = LoggerFactory.getLogger(GroupService.class);
	@Autowired
	private GroupDao groupDao;
	@Autowired
	protected ChatDao chatDao;
	@Autowired
	private UserChatDao userChatDao;
	@Autowired
	private UserService userService;
	@Autowired
	private FriendService friendService;
	@Autowired
	private MessageTextBuilder messageTextBuilder;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RedisService redisService;

	@Override
	@Transactional(readOnly = true)
	public List<Group> getGroups() {
		// Get logged user
		User loggedUser = userService.getLoggedUserEntity();
		logger.debug("-> getGroups for user : {}", loggedUser.getUserId());

		return groupDao.findByOwner(loggedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public Group getGroup(long groupId) {
		// Get loggedUser
		long userId = userService.getLoggedUserId();
		logger.debug("getGroup: groupId: {}", groupId);

		Group group = DaoValidation.groupExistValidation(groupDao, groupId);
		// Validation
		isUserInGroup(userId, group.getChatId(), true);

		return group;
	}

	@Override
	@Transactional
	public Group createGroup(String name, String[] userIds) {
		// Get loggedUser
		User loggedUser = userService.getLoggedUserEntity();
		logger.debug("createGroup : name = {}, adminId  = {}", name, loggedUser.getUserId());

		// TODO Validate group name

		// Create user list
		Set<User> usersList = new HashSet<>();
		usersList.add(loggedUser);
		if (Objects.nonNull(userIds)) {
			int size = userIds.length;
			for (int i = 0; i < size; ++i) {
				if (Objects.nonNull(userIds[i])) {
					User user = userService.getUserById(Long.valueOf(userIds[i]));
					isYourFriend(loggedUser.getUserId(), user.getUserId());
					usersList.add(user);
				}
			}
		}

		// Create chat
		Chat chat = chatDao.merge(new Chat());

		// Create group
		Group group = groupDao.merge(new Group(chat, name, loggedUser));

		// Create messages
		for (User user : usersList) {
			// Set chat name
			userChatDao.merge(new UserChat(chat, user, name));

			String messageText = null;
			if (user.getUserId() == loggedUser.getUserId()) {
				messageText = messageTextBuilder.createOneParamMessage(CREATE_GROUP_MESSAGE,
						loggedUser.getUserFullName());
			} else {
				messageText = messageTextBuilder.createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE,
						group.getAdmin().getUserFullName(), user.getUserFullName());
			}
			sendMessageToRedis(messageText, group, loggedUser);
		}

		return group;
	}

	@Override
	@Transactional
	public User addUserToGroup(long groupId, long invitedUserId) {
		logger.debug("-> addUserToGroup : groupId = {}, invitedUserId = {}", groupId, invitedUserId);
		User loggedUser = userService.getLoggedUserEntity();
		User invitedUser = userService.getUserById(invitedUserId);

		Group group = DaoValidation.groupExistValidation(groupDao, groupId);
		
		addUserToGroupValidation(loggedUser.getUserId(), invitedUser.getUserId(), group);

		userChatDao.merge(new UserChat(group.getChat(), invitedUser, group.getGroupName()));
		String messageText = messageTextBuilder.createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE,
				loggedUser.getUserFullName(), invitedUser.getFirstName());
		
		sendMessageToRedis(messageText, group, loggedUser);
		return invitedUser;
	}

	@Override
	@Transactional
	public User deleteUserFromGroup(long groupId, long removedUserId) {
		logger.debug("-> deleteUserFromGroup : groupId = {}, removedUserId = {}", groupId, removedUserId);
		User loggedUser = userService.getLoggedUserEntity();
		User removedUser = userService.getUserById(removedUserId);

		Group group = DaoValidation.groupExistValidation(groupDao, groupId);
		
		deleteUserFromGroupValidation(loggedUser, removedUser, group);

		userChatDao.removeUserFromChat(group.getChat(), removedUser);
		String messageText = messageTextBuilder.createTwoParamsMessage(DELETE_USER_FROM_GROUP_MESSAGE,
				loggedUser.getFirstName(), removedUser.getUserFullName());
		
		sendMessageToRedis(messageText, group, loggedUser);
		return removedUser;
	}

	@Override
	@Transactional
	public Group leaveGroup(long groupId) {
		logger.debug("leaveGroup : groupId = {}", groupId);
		User loggedUser = userService.getLoggedUserEntity();
		Group group = DaoValidation.groupExistValidation(groupDao, groupId);

		leaveGroupValidation(loggedUser, group);

		userChatDao.removeUserFromChat(group.getChat(), loggedUser);
		String messageText = messageTextBuilder.createOneParamMessage(LEAVE_GROUP_MESSAGE,
				loggedUser.getUserFullName());

		sendMessageToRedis(messageText, group, loggedUser);
		return group;
	}

	@Override
	@Transactional
	public Group deleteGroup(long groupId) {
		logger.debug("-> deleteGroup : groupId: {}", groupId);
		User loggedUser = userService.getLoggedUserEntity();
		Group group = DaoValidation.groupExistValidation(groupDao, groupId);

		deleteGroupValidation(loggedUser, group);

		group.getChat().hiddeChat();
		group.hiddeGroup();
		String messageText = messageTextBuilder.createOneParamMessage(DELETE_GROUP_MESSAGE,
				loggedUser.getUserFullName());

		sendMessageToRedis(messageText, group, loggedUser);
		return group;
	}

	private boolean sendMessageToRedis(String messageText, Group group, User loggedUser) {
		Message message = messageService.createSystemMessage(messageText, loggedUser, group.getChat(),
				SystemMessageStatus.SYSTEM);
		return redisService.sendMessageToRedis(message);
	}

	/*
	 * Custom action validation
	 */

	private void addUserToGroupValidation(long loggedUser, long invitedUser, Group group) {
		logger.debug("-> addUserToGroupValidation ");
		isAdminAction(loggedUser, group.getAdmin().getUserId(), true);
		isUserInGroup(invitedUser, group.getChatId(), false);
		isYourFriend(loggedUser, invitedUser);
	}

	private void deleteUserFromGroupValidation(User loggedUser, User deletedUser, Group group) {
		logger.debug("-> deleteUserFromGroupValidation ");
		isAdminAction(loggedUser.getUserId(), group.getAdmin().getUserId(), true);
		if (loggedUser.getUserId() == deletedUser.getUserId()) {
			throw new GroupAdminException("You are group admin! You can't delete himself");
		}
		isUserInGroup(deletedUser.getUserId(), group.getChatId(), true);
		isGroupRemoved(group);
	}

	private void leaveGroupValidation(User loggedUser, Group group) {
		logger.debug("-> leaveGroupValidation ");
		isAdminAction(loggedUser.getUserId(), group.getAdmin().getUserId(), false);
		isUserInGroup(loggedUser.getUserId(), group.getChatId(), true);
		isGroupRemoved(group);
	}

	private void deleteGroupValidation(User loggedUser, Group group) {
		logger.debug("-> deleteGroupValidation ");
		isAdminAction(loggedUser.getUserId(), group.getAdmin().getUserId(), true);
		isGroupRemoved(group);
	}

	/*
	 * General validation
	 */
	private void isGroupRemoved(Group group) {
		if (group.isHidden()) {
			throw new DeleteGroupException("The group has already removed!");
		}
	}

	private void isAdminAction(long userId, long adminId, boolean adminAction) {
		logger.debug("-> isAdminAction ");

		if ((userId != adminId) == adminAction) {
			throw new GroupAdminException("This is admin action. You are not group admin!");
		} else if ((userId == adminId) != adminAction) {
			throw new GroupAdminException("This isn't admin action. You are group admin!");
		}
	}

	private void isUserInGroup(long userId, long chatId, boolean userStatus) {
		logger.debug("-> isUserInGroup: chatId: {}, userId: {}", chatId, userId);

		UserChat userChat = userChatDao.findByChatAndUser(chatId, userId);

		if (Objects.isNull(userChat) && userStatus) {
			throw new GroupPermissionExceptions("You are not in this group");
		} else if (Objects.nonNull(userChat) && !userStatus) {
			throw new GroupPermissionExceptions("User already in this group");
		}
	}

	private void isYourFriend(long loggedUser, long invitedUser) {
		logger.debug("-> isYourFriend ");
		if (!friendService.isYourFriend(invitedUser, loggedUser)) {
			throw new GroupPermissionExceptions("You catn't add this user!");
		}
	}

}
