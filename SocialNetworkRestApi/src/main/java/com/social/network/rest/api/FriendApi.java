package com.social.network.rest.api;

import static com.social.network.rest.utils.Constants.ACCEPT_INVITATION_PATH;
import static com.social.network.rest.utils.Constants.DECLINE_INVITATION_PATH;
import static com.social.network.rest.utils.Constants.FRIEND_PATH;
import static com.social.network.rest.utils.Constants.INVITE_FRIEND_PATH;
import static com.social.network.rest.utils.Constants.USER_PARAM;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.rest.dto.FriendDto;
import com.social.network.rest.facade.FriendServiceFacade;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */
@RestController
@RequestMapping(value = FRIEND_PATH)
public class FriendApi {

	private static final Logger logger = LoggerFactory.getLogger(FriendApi.class);
	
	@Autowired
	private FriendServiceFacade friendFacade;

	@RequestMapping(method = RequestMethod.GET)
	public List<FriendDto> getFriends() {
		logger.info(" getFriends ");
		return friendFacade.getFriends();
	}

	@RequestMapping(value = INVITE_FRIEND_PATH + USER_PARAM, method = RequestMethod.POST)
	public FriendDto inviteFriend(@PathVariable("userId") long userId) {
		return friendFacade.inviteFriend(userId);
	}

	@RequestMapping(value = ACCEPT_INVITATION_PATH + USER_PARAM, method = RequestMethod.POST)
	public FriendDto accceptInvitation(@PathVariable("userId") long userId) {
		return friendFacade.acceptInvitation(userId);
	}

	@RequestMapping(value = DECLINE_INVITATION_PATH + USER_PARAM, method = RequestMethod.POST)
	public FriendDto declineInvitation(@PathVariable("userId") long userId) {
		return friendFacade.declineInvitation(userId);
	}

	@RequestMapping(value = USER_PARAM, method = RequestMethod.DELETE)
	public FriendDto deleteFriend(@PathVariable("userId") long userId) {
		return friendFacade.deleteFriend(userId);
	}
}
