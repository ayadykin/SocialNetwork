package com.social.network.rest.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.UserChat;
import com.social.network.rest.dto.FriendDto;
import com.social.network.rest.dto.chat.ChatDto;
import com.social.network.rest.dto.group.GroupDto;
import com.social.network.rest.dto.group.GroupUserDto;

/**
 * Created by Yadykin Andrii Jul 14, 2016
 *
 */

public class EntityToDtoMapper {

    private static final Logger logger = LoggerFactory.getLogger(EntityToDtoMapper.class);  

    public static ChatDto convertChatToChatDto(UserChat chat) {
        return new ChatDto(chat.getChat().getChatId(), chat.getChatName(), chat.getChat().getHidden());
    }

    public static FriendDto convertFriendToFriendDto(Friend friend) {
        logger.debug(" convertFriendToFriendDto friend : {} ", friend);
        return new FriendDto(friend.getFriendName(), friend.getFriendStatus(), friend.getChat().getChatId(),
                friend.getFriend().getUserId());
    }

    public static List<GroupUserDto> convertUserToGroupUserDto(Set<UserChat> users, long adminId) {
        logger.debug(" convertUserToGroupUserDto ");
        List<GroupUserDto> usersList = new ArrayList<>();
        for (UserChat user : users) {
            if (user.getUserId() == adminId) {
                usersList.add(new GroupUserDto(user.getUserId(), user.getUserFullName(), true));
            } else {
                usersList.add(new GroupUserDto(user.getUserId(), user.getUserFullName(), false));
            }
        }
        return usersList;
    }

    public static GroupDto convertGroupToGroupsDto(Group group, long loggedUserId, boolean withUsers) {
        return convertGroupsToGroupsDto(Arrays.asList(group), loggedUserId, withUsers).iterator().next();
    }

    public static Set<GroupDto> convertGroupsToGroupsDto(List<Group> groups, long loggedUserId, boolean withUsers) {
        logger.debug(" convertGroupsToGroupsDto groups size {} ", groups.size());
        Set<GroupDto> groupsDtoList = new LinkedHashSet<>();
        for (Group group : groups) {
            boolean isAdmin = loggedUserId == group.getAdmin().getUserId();

            List<GroupUserDto> users = null;
            if (withUsers) {
                users = convertUserToGroupUserDto(group.getChat().getUserChat(), group.getAdmin().getUserId());
            }

            groupsDtoList.add(new GroupDto(group.getGroupName(), group.getGroupId(), group.getChatId(), users, isAdmin, group.getHidden()));
        }
        logger.debug(" convertGroupsToGroupsDto groups {} ", groupsDtoList);
        return groupsDtoList;
    }
}
