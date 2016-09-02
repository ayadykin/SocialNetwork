package com.social.network.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.model.Friend;
import com.social.network.dto.FriendDto;
import com.social.network.services.FriendService;
import com.social.network.utils.EntityToDtoMapper;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Service
public class FriendServiceFacade {
	@Autowired
	private FriendService friendService;

	@Transactional
	public List<FriendDto> getFriends() {
		List<FriendDto> friendsDto = new ArrayList<>();
		for (Friend friend : friendService.getFriends()) {
			friendsDto.add(EntityToDtoMapper.convertFriendToFriendDto(friend));
		}
		return friendsDto;
	}

	@Transactional
	public FriendDto inviteFriend(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.inviteFriend(userId));
	}

	@Transactional
	public FriendDto acceptInvitation(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.acceptInvitation(userId));
	}

	@Transactional
	public FriendDto declineInvitation(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.declineInvitation(userId));
	}

	@Transactional
	public FriendDto deleteFriend(long userId) {
		return EntityToDtoMapper.convertFriendToFriendDto(friendService.deleteFriend(userId));
	}

}
