package com.social.network.rest.api;

import static com.social.network.rest.utils.Constants.ADD_USER;
import static com.social.network.rest.utils.Constants.DELETE_USER;
import static com.social.network.rest.utils.Constants.GROUP_PARAM;
import static com.social.network.rest.utils.Constants.GROUP_PATH;
import static com.social.network.rest.utils.Constants.LEAVE_GROUP;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.rest.dto.group.CreateGroupDto;
import com.social.network.rest.dto.group.GroupDto;
import com.social.network.rest.dto.group.GroupUserDto;
import com.social.network.rest.dto.group.UserGroupActionsDto;
import com.social.network.rest.facade.GroupServiceFacade;
import com.social.network.utils.RestResponse;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@RestController
@RequestMapping(value = GROUP_PATH)
public class GroupApi {

	private static final Logger logger = LoggerFactory.getLogger(GroupApi.class);

	@Autowired
	private GroupServiceFacade groupServiceFacade;

	@RequestMapping(method = RequestMethod.GET)
	public Set<GroupDto> getGroups() {
		return groupServiceFacade.getGroups();
	}

	@RequestMapping(method = RequestMethod.POST)
	public GroupDto createGroup(@RequestBody CreateGroupDto createGroupDto) {
		logger.debug(" createGroup createGroupDto : {} ", createGroupDto);
		return groupServiceFacade.createGroup(createGroupDto.getGroupName(), createGroupDto.getFriendsId(),
				createGroupDto.getPublicGroup());
	}

	@RequestMapping(value = GROUP_PARAM, method = RequestMethod.GET)
	public GroupDto getGroup(@PathVariable("groupId") long groupId) {
		return groupServiceFacade.getGroup(groupId);
	}

	@RequestMapping(value = "/edit" +  GROUP_PARAM, method = RequestMethod.GET)
	public GroupDto editGroup(@PathVariable("groupId") long groupId) {
		return groupServiceFacade.getGroup(groupId);
	}

	@RequestMapping(value = ADD_USER, method = RequestMethod.POST)
	public GroupUserDto addUserToGroup(@RequestBody UserGroupActionsDto groupUserDto) {
		return groupServiceFacade.addUserToGroup(groupUserDto.getGroupId(), groupUserDto.getUserId());
	}

	@RequestMapping(value = DELETE_USER, method = RequestMethod.PUT)
	public GroupUserDto deleteUser(@RequestBody UserGroupActionsDto groupUserDto) {
		return groupServiceFacade.deleteUserFromGroup(groupUserDto.getGroupId(), groupUserDto.getUserId());
	}

	@RequestMapping(value = LEAVE_GROUP + GROUP_PARAM, method = RequestMethod.DELETE)
	public GroupDto leaveGroup(@PathVariable("groupId") long groupId) {
		return groupServiceFacade.leaveGroup(groupId);
	}

	@RequestMapping(value = GROUP_PARAM, method = RequestMethod.DELETE)
	public GroupDto deleteGroup(@PathVariable("groupId") long groupId) {
		return groupServiceFacade.deleteGroup(groupId);
	}
}
