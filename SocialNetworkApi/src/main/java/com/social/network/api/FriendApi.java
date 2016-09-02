package com.social.network.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.social.network.dto.FriendDto;
import com.social.network.facade.FriendServiceFacade;

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
	public FriendDto inviteFriend(@PathVariable("userId") long userId) {
		return friendFacade.inviteFriend(userId);
	}

	@ResponseBody
	@RequestMapping(value = "/acceptInvitation/{userId}", method = RequestMethod.POST)
	public FriendDto accceptInvitation(@PathVariable("userId") long userId) {
		return friendFacade.acceptInvitation(userId);
	}

	@ResponseBody
	@RequestMapping(value = "/declineInvitation/{userId}", method = RequestMethod.POST)
	public FriendDto declineInvitation(@PathVariable("userId") long userId) {
		return friendFacade.declineInvitation(userId);
	}

	@ResponseBody
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public FriendDto deleteFriend(@PathVariable("userId") long userId) {
		return friendFacade.deleteFriend(userId);
	}
}
