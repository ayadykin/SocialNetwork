package com.social.network.api;

import static com.social.network.utils.Constants.ADD_USER;
import static com.social.network.utils.Constants.DELETE_USER;
import static com.social.network.utils.Constants.LEAVE_GROUP;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.dto.GroupDto;
import com.social.network.dto.group.CreateGroupDto;
import com.social.network.dto.group.GroupUserDto;
import com.social.network.facade.GroupServiceFacade;
import com.social.network.utils.RestResponse;
import com.social.network.utils.ResultToResponseWrapper;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@RestController
@RequestMapping(value = "/group")
public class GroupApi {

    private static final Logger logger = LoggerFactory.getLogger(GroupApi.class);

    @Autowired
    private GroupServiceFacade groupServiceFacade;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Set<GroupDto> getGroups() {
        return groupServiceFacade.getGroups();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public GroupDto createGroup(@RequestBody CreateGroupDto createGroupDto) {
        logger.debug(" createGroup createGroupDto : {} ", createGroupDto);
        return groupServiceFacade.createGroup(createGroupDto.getGroupName(), createGroupDto.getFriendsId());
    }

    @ResponseBody
    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    public GroupDto getGroup(@PathVariable("groupId") long groupId) {
        return groupServiceFacade.getGroup(groupId);
    }

    @ResponseBody
    @RequestMapping(value = "/edit/{groupId}", method = RequestMethod.GET)
    public GroupDto editGroup(@PathVariable("groupId") long groupId) {
        // mav.addObject("friends", groupService.getFriendsNotInGroup(groupId));
        return groupServiceFacade.getGroup(groupId);
    }

    @ResponseBody
    @RequestMapping(value = "/friends_not_in_group/{groupId}", method = RequestMethod.GET)
    public List<GroupUserDto> getFriendsNotInGroup(@PathVariable("groupId") long groupId) {
        return groupServiceFacade.getFriendsNotInGroup(groupId);
    }

    @ResponseBody
    @RequestMapping(value = ADD_USER, method = RequestMethod.POST)
    public String addUserToGroup(@RequestBody GroupUserDto groupUserDto) {
        return ResultToResponseWrapper
                .convert(() -> groupServiceFacade.addUserToGroup(groupUserDto.getGroupId(), groupUserDto.getUserId()));
    }

    @ResponseBody
    @RequestMapping(value = DELETE_USER, method = RequestMethod.PUT)
    public RestResponse deleteUser(@RequestBody GroupUserDto groupUserDto) {
        return new RestResponse()
                .convert(() -> groupServiceFacade.deleteUserFromGroup(groupUserDto.getGroupId(), groupUserDto.getUserId()));
    }

    @ResponseBody
    @RequestMapping(value = LEAVE_GROUP + "/{groupId}", method = RequestMethod.DELETE)
    public String leaveGroup(@PathVariable("groupId") long groupId) {
        return ResultToResponseWrapper.convert(() -> groupServiceFacade.leaveGroup(groupId));
    }

    @ResponseBody
    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public RestResponse deleteGroup(@PathVariable("groupId") long groupId) {
        return new RestResponse().convert(() -> groupServiceFacade.deleteGroup(groupId));
    }
}
