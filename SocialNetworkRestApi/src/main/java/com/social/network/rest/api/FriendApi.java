package com.social.network.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.rest.dto.FriendDto;
import com.social.network.rest.facade.FriendServiceFacade;
import com.social.network.utils.RestResponse;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */
@Controller
@RequestMapping(value = "/friend")
public class FriendApi {

    @Autowired
    private FriendServiceFacade friendFacade;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<FriendDto> getFriends() {
        return friendFacade.getFriends();
    }

    @ResponseBody
    @RequestMapping(value = "/inviteFriend/{userId}", method = RequestMethod.POST)
    public RestResponse inviteFriend(@PathVariable("userId") long userId) {
        return new RestResponse().convert(() -> friendFacade.inviteFriend(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/acceptInvitation/{userId}", method = RequestMethod.POST)
    public RestResponse accceptInvitation(@PathVariable("userId") long userId) {
        return new RestResponse().convert(() -> friendFacade.acceptInvitation(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/declineInvitation/{userId}", method = RequestMethod.POST)
    public RestResponse declineInvitation(@PathVariable("userId") long userId) {
        return new RestResponse().convert(() -> friendFacade.declineInvitation(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public RestResponse deleteFriend(@PathVariable("userId") long userId) {
        return new RestResponse().convert(() -> friendFacade.deleteFriend(userId));
    }
}
