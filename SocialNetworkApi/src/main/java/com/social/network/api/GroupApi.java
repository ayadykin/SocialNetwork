package com.social.network.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.facade.GroupServiceFacade;
import com.social.network.utils.ResultToResponseWrapper;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@Controller
@RequestMapping(value = "/group")
public class GroupApi {

    @Autowired
    private GroupServiceFacade groupServiceFacade;

    @RequestMapping(value = "/creategroup", method = RequestMethod.POST)
    public String createGroup(String groupName, String[] friendsId) {
        groupServiceFacade.createGroup(groupName, friendsId);
        return "redirect:/group";
    }

    @ResponseBody
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUserToGroup(@RequestParam("groupId") long groupId, @RequestParam("userId") long userId) {
        return ResultToResponseWrapper.convert(() -> groupServiceFacade.addUserToGroup(groupId, userId));
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("groupId") long groupId, @RequestParam("userId") long userId) {
        return ResultToResponseWrapper.convert(() -> groupServiceFacade.deleteUserFromGroup(groupId, userId));
    }
    
    @ResponseBody
    @RequestMapping(value = "/leavegroup", method = RequestMethod.POST)
    public String leaveGroup(long groupId) {
        return ResultToResponseWrapper.convert(() -> groupServiceFacade.leaveGroup(groupId));
    }
    
    @ResponseBody
    @RequestMapping(value = "/deletegroup", method = RequestMethod.POST)
    public String deleteGroup(@RequestParam("groupId") long groupId) {
        return ResultToResponseWrapper.convert(() -> groupServiceFacade.deleteGroup(groupId));
    }
}
