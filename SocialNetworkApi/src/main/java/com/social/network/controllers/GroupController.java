package com.social.network.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.social.network.facade.FriendServiceFacade;
import com.social.network.facade.GroupServiceFacade;
import com.social.network.services.GroupService;

/**
 * Created by andrii.perylo on 5/18/2016.
 */
@Controller
@RequestMapping(value = "/group")
public class GroupController {

    private static final String GROUPS_LIST_VIEW_NAME = "group/groupsList";
    private static final String CREATE_GROUP_VIEW_NAME = "group/createGroup";
    private static final String GROUP_VIEW_NAME = "group/group";
    private static final String GROUP_EDIT_VIEW_NAME = "group/editGroup";

    private static final String GROUPS_LIST_ATTRIBUTE = "groups_list";
    private static final String USER_FRIEND_LIST = "friend_list";

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupServiceFacade groupServiceFacade;
    @Autowired
    private FriendServiceFacade friendFacade;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getGroups() {
        ModelAndView mav = new ModelAndView(GROUPS_LIST_VIEW_NAME);
        mav.addObject(GROUPS_LIST_ATTRIBUTE, groupServiceFacade.getGroups());
        return mav;
    }

    @RequestMapping(value = "/creategroup", method = RequestMethod.GET)
    public ModelAndView initCreateGroup() {
        ModelAndView mav = new ModelAndView(CREATE_GROUP_VIEW_NAME);
        mav.addObject(USER_FRIEND_LIST, friendFacade.getFriends());
        return mav;
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    public ModelAndView getGroup(@PathVariable("groupId") long groupId) {
        ModelAndView mav = new ModelAndView(GROUP_VIEW_NAME);
        mav.addObject("group", groupServiceFacade.getGroup(groupId));
        return mav;
    }

    @RequestMapping(value = "/edit/{groupId}", method = RequestMethod.GET)
    public ModelAndView editGroup(@PathVariable("groupId") long groupId) {
        ModelAndView mav = new ModelAndView(GROUP_EDIT_VIEW_NAME);
        mav.addObject("group", groupServiceFacade.getGroup(groupId));
        mav.addObject("friends", groupService.getFriendsNotInGroup(groupId));
        return mav;
    }

}
