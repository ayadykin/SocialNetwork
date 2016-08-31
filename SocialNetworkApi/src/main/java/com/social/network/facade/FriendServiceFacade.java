package com.social.network.facade;

import static com.social.network.utils.Constants.ACCEPT_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.DECLINE_INVITATION_MESSAGE;
import static com.social.network.utils.Constants.INVITATION_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.FriendTemplate;
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
	@Autowired
	@Qualifier("inviteFriend")
	private FriendTemplate inviteFriend;
	@Autowired
	@Qualifier("acceptInvitation")
	private FriendTemplate acceptInvitation;
	@Autowired
	@Qualifier("declineInvitation")
	private FriendTemplate declineInvitation;
	
	@Transactional
	public List<FriendDto> getFriends() {
		List<FriendDto> friendsDto = new ArrayList<>();
		for (Friend friend : friendService.getFriends()) {
			friendsDto.add(EntityToDtoMapper.convertFriendToFriendDto(friend));
		}
		return friendsDto;
	}

	@Transactional
	public boolean inviteFriend(long userId) {
		return inviteFriend.friendAction(userId, INVITATION_MESSAGE);
	}

	@Transactional
	public boolean acceptInvitation(long userId) {
		return acceptInvitation.friendAction(userId, ACCEPT_INVITATION_MESSAGE);
	}

	@Transactional
	public boolean declineInvitation(long userId) {
		return declineInvitation.friendAction(userId, DECLINE_INVITATION_MESSAGE);
	}

	@Transactional
	public boolean deleteFriend(long friendId) {
		return friendService.deleteFriend(friendId);
	}

}
