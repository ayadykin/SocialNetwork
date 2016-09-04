package com.social.network.rest.facade;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.model.Friend;
import com.social.network.rest.dto.FriendDto;
import com.social.network.rest.utils.EntityToDtoMapper;
import com.social.network.services.FriendService;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Service
public class FriendServiceFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(FriendServiceFacade.class);
	
	@Autowired
	private FriendService friendService;

	@Transactional(readOnly = true)
	public List<FriendDto> getFriends() {
		List<FriendDto> friendsDto = new ArrayList<>();
		for (Friend friend : friendService.getFriends()) {
			friendsDto.add(EntityToDtoMapper.convertFriendToFriendDto(friend));
		}
		return friendsDto;
	}

	
	public FriendDto inviteFriend(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.inviteFriend(userId));
	}

	
	public FriendDto acceptInvitation(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.acceptInvitation(userId));
	}

	
	public FriendDto declineInvitation(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.declineInvitation(userId));
	}

	
	public FriendDto deleteFriend(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.deleteFriend(userId));
	}

}
