package com.social.network.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.domain.model.Friend;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.SystemMessage;
import com.social.network.domain.model.UserChat;
import com.social.network.dto.FriendDto;
import com.social.network.dto.GroupDto;
import com.social.network.dto.MessageDto;
import com.social.network.dto.chat.ChatDto;
import com.social.network.dto.group.GroupUserDto;

/**
 * Created by Yadykin Andrii Jul 14, 2016
 *
 */

public class EntityToDtoMapper {

    private static final Logger logger = LoggerFactory.getLogger(EntityToDtoMapper.class);
    
    public static MessageDto convertMessageToMessageDto(Message message) {
        logger.debug("strart convertMessageToMessageDto : message = {} ", message);

        // Validate chat
        long chatId = 0;
        if (message.getChat().size() == 1) {
            chatId = message.getChat().iterator().next().getChatId();
        }

       /* if (chatId <= 0) {
            throw new ConvertMessageException("Can't convert message without chat id");
        }*/
        // Get message's owner name
        String ownerName = message.getPublisher().getUserFullName();
        MessageDto messageDto = new MessageDto(chatId, message.getMessageId(), message.getText(), message.getCreateDate(), ownerName,
                message.getPublisherId());

        // Get invitation message flag
        if (message instanceof SystemMessage) {
            messageDto.setMessageInviteStatus(((SystemMessage) message).getSystemMessageStatus());
        }
        // Get hidden message flag
        if (message.getHidden().isHidden()) {
            messageDto.setHidden(true);
            messageDto.setText("This message has been removed.");
        }

        logger.debug("end convertMessageToMessageDto : messageDto = {}", messageDto);
        return messageDto;
    }

    public static ChatDto convertChatToChatDto(UserChat chat) {
        return new ChatDto(chat.getChat().getChatId(), chat.getChatName(), chat.getChat().getHidden());
    }

    public static FriendDto convertFriendToFriendDto(Friend friend) {
        logger.debug(" convertFriendToFriendDto ");
        return new FriendDto(friend.getFriendName(), friend.getFriendStatus(), friend.getChat().getChatId(),
                friend.getFriend().getUserId());
    }

    public static List<GroupUserDto> convertUserToGroupUserDto(Set<UserChat> users, long adminId) {
        List<GroupUserDto> usersList = new ArrayList<>();
        for (UserChat user : users) {
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
