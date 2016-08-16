package com.social.network.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.dto.FriendDto;
import com.social.network.facade.FriendServiceFacade;
import com.social.network.utils.RestResponse;
import com.social.network.utils.ResultToResponseWrapper;

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
    @RequestMapping(value = "/inviteFriend", method = RequestMethod.POST)
    public String inviteFriend(@RequestParam("userId") long userId) {
        return ResultToResponseWrapper.convert(() -> friendFacade.inviteFriend(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/acceptInvitation", method = RequestMethod.POST)
    public RestResponse accceptInvitation(@RequestParam("userId") long userId) {
        return new RestResponse().convert(() -> friendFacade.acceptInvitation(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/declineInvitation", method = RequestMethod.POST)
    public RestResponse declineInvitation(@RequestParam("userId") long userId) {
        return new RestResponse().convert(() -> friendFacade.declineInvitation(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/{friendId}", method = RequestMethod.DELETE)
    public RestResponse deleteFriend(@PathVariable("friendId") long friendId) {
        return new RestResponse().convert(() -> friendFacade.deleteFriend(friendId));
    }
}
