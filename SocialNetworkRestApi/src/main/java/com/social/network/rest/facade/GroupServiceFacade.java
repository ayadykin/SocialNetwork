package com.social.network.rest.facade;

import static com.social.network.utils.Constants.CREATE_PUBLIC_GROUP_MESSAGE;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.message.FriendsMailing;
import com.social.network.core.message.text.MessageTextBuilder;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.User;
import com.social.network.rest.dto.group.GroupDto;
import com.social.network.rest.dto.group.GroupUserDto;
import com.social.network.rest.utils.EntityToDtoMapper;
import com.social.network.services.GroupService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@Service
public class GroupServiceFacade {

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceFacade.class);

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageTextBuilder messageTextBuilder;
	@Autowired
	private FriendsMailing friendsMailing;
	
	public GroupDto createGroup(String name, String[] userList, boolean publicGroup) {
		long userId = userService.getLoggedUserId();

		Group group = groupService.createGroup(name, userList);
		
		// Mailing
		if (publicGroup) {
			String messageText = messageTextBuilder.createTwoParamsMessage(CREATE_PUBLIC_GROUP_MESSAGE,
					group.getAdmin().getUserFullName(), name);
			friendsMailing.mailing(messageText, group.getAdmin());
		}
		return EntityToDtoMapper.convertGroupToGroupsDto(group, userId, false);
	}

	public Set<GroupDto> getGroups() {
		long userId = userService.getLoggedUserId();
		return EntityToDtoMapper.convertGroupsToGroupsDto(groupService.getGroups(), userId, false);
	}

	@Transactional(readOnly = true)
	public GroupDto getGroup(long groupId) {
		long userId = userService.getLoggedUserId();

		Group group = groupService.getGroup(groupId);

		return EntityToDtoMapper.convertGroupToGroupsDto(group, userId, true);
	}

	public GroupUserDto addUserToGroup(long groupId, long userId) {

		User user = groupService.addUserToGroup(groupId, userId);

		return new GroupUserDto(userId, user.getUserFullName(), false);
	}

	public GroupUserDto deleteUserFromGroup(long groupId, long userId) {

		User user = groupService.deleteUserFromGroup(groupId, userId);
		return new GroupUserDto(userId, user.getUserFullName(), false);
	}

	public GroupDto leaveGroup(long groupId) {
		long userId = userService.getLoggedUserId();
		Group group = groupService.leaveGroup(groupId);

		return EntityToDtoMapper.convertGroupToGroupsDto(group, userId, false);
	}

	public GroupDto deleteGroup(long groupId) {
		long userId = userService.getLoggedUserId();
		Group group = groupService.deleteGroup(groupId);

		return EntityToDtoMapper.convertGroupToGroupsDto(group, userId, false);
	}

}
