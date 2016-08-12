package com.social.network.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.dto.ChatDto;
import com.social.network.dto.FriendDto;
import com.social.network.dto.GroupDto;
import com.social.network.dto.GroupUserDto;
import com.social.network.dto.MessageDto;
import com.social.network.exceptions.chat.ConvertMessageException;
import com.social.network.model.Friend;
import com.social.network.model.Group;
import com.social.network.model.Message;
import com.social.network.model.SystemMessage;
import com.social.network.model.User;
import com.social.network.model.UserChat;

/**
 * Created by Yadykin Andrii Jul 14, 2016
 *
 */

public class EntityToDtoMapper {

    private static final Logger logger = LoggerFactory.getLogger(EntityToDtoMapper.class);

    public static MessageDto convertMessageToMessageDto(Message message, long userId) {
        logger.debug("strart convertMessageToMessageDto : message = {}, userId = {}", message, userId);

        // Validate chat
        long chatId = message.getChatId();
        if (chatId <= 0) {
            throw new ConvertMessageException("Can't convert message without chat id");
        }
        // Get message's owner name
        String ownerName = message.getPublisher().getUserFullName();
        MessageDto messageDto = new MessageDto(message.getChatId(), message.getMessageId(), message.getText(), message.getCreateDate(),
                ownerName, message.getPublisherId());

        // Get invitation message flag
        if (message instanceof SystemMessage) {
            messageDto.setMessageInviteStatus(((SystemMessage) message).getSystemMessageStatus());
        }
        // Get hidden message flag
        if (message.getHidden().isHidden()) {
            messageDto.setHidden(true);
            messageDto.setText("");
        }

        logger.debug("end convertMessageToMessageDto : messageDto = {}", messageDto);
        return messageDto;
    }

    public static ChatDto convertChatToChatDto(UserChat chat) {
        return new ChatDto(chat.getChat().getChatId(), chat.getChatName(), chat.getChat().getHidden());
    }

    public static FriendDto convertFriendToFriendDto(Friend friend) {
        logger.debug(" convertFriendToFriendDto ");
        return new FriendDto(friend.getFriend().getUserId(), friend.getFriendName(), friend.getFriendStatus(),
                friend.getChat().getChatId());
    }

    public static Set<GroupUserDto> convertUserToGroupUserDto(Set<User> users, long adminId) {
        Set<GroupUserDto> usersList = new HashSet<>();
        for (User user : users) {
            boolean isAdmin = user.getUserId() == adminId;
            if (isAdmin) {
                usersList.add(new GroupUserDto(user.getUserId(), user.getUserFullName(), isAdmin));
            } else {
                usersList.add(new GroupUserDto(user.getUserId(), user.getUserFullName(), isAdmin));
            }
        }
        return usersList;
    }

    public static GroupDto convertGroupToGroupsDto(Group group, long loggedUserId, boolean withUsers) {
        return convertGroupsToGroupsDto(Arrays.asList(group), loggedUserId, withUsers).iterator().next();
    }

    public static Set<GroupDto> convertGroupsToGroupsDto(List<Group> groups, long loggedUserId, boolean withUsers) {
        logger.debug(" convertGroupsToGroupsDto groups size {} ", groups.size());
        Set<GroupDto> groupsDtoList = new HashSet<>();
        for (Group group : groups) {
            boolean isAdmin = loggedUserId == group.getAdminId();

            Set<GroupUserDto> users = null;
            if (withUsers) {
                users = convertUserToGroupUserDto(group.getChat().getUsers(), group.getAdminId());
            }

            groupsDtoList.add(
                    new GroupDto(group.getGroupName(), group.getGroupId(), group.getChatId(), users, isAdmin, group.getChat().getHidden()));
        }
        logger.debug(" convertGroupsToGroupsDto groups {} ", groupsDtoList);
        return groupsDtoList;
    }
}
