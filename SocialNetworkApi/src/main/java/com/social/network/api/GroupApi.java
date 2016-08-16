package com.social.network.api;

import static com.social.network.utils.Constants.DELETE_USER;
import static com.social.network.utils.Constants.LEAVE_ROUP;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.dto.GroupDto;
import com.social.network.dto.GroupUserDto;
import com.social.network.facade.GroupServiceFacade;
import com.social.network.utils.RestResponse;
import com.social.network.utils.ResultToResponseWrapper;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@Controller
@RequestMapping("/group")
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
    public GroupDto createGroup(@RequestParam("groupName") String groupName, @RequestParam("friendsId") String[] friendsId) {
        logger.debug(" createGroup groupName : {} ", groupName);
        return groupServiceFacade.createGroup(groupName, friendsId);
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
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUserToGroup(@RequestParam("groupId") long groupId, @RequestParam("userId") long userId) {
        return ResultToResponseWrapper.convert(() -> groupServiceFacade.addUserToGroup(groupId, userId));
    }

    @ResponseBody
    @RequestMapping(value = DELETE_USER, method = RequestMethod.PUT)
    public RestResponse deleteUser(@RequestParam("groupId") long groupId, @RequestParam("userId") long userId) {
        return new RestResponse().convert(() -> groupServiceFacade.deleteUserFromGroup(groupId, userId));
    }

    @ResponseBody
    @RequestMapping(value = LEAVE_ROUP + "/{groupId}", method = RequestMethod.DELETE)
    public String leaveGroup(@PathVariable("groupId") long groupId) {
        return ResultToResponseWrapper.convert(() -> groupServiceFacade.leaveGroup(groupId));
    }

    @ResponseBody
    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public RestResponse deleteGroup(@PathVariable("groupId") long groupId) {
        return new RestResponse().convert(() -> groupServiceFacade.deleteGroup(groupId));
    }
}
