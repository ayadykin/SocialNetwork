package com.social.network.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.dto.ChatDto;
import com.social.network.dto.FriendDto;
import com.social.network.dto.GroupDto;
import com.social.network.dto.GroupUserDto;
import com.social.network.dto.MessageDto;
import com.social.network.exceptions.chat.ConvertMessageException;
import com.social.network.model.Chat;
import com.social.network.model.Friend;
import com.social.network.model.Group;
import com.social.network.model.Message;
import com.social.network.model.User;

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
        if (Objects.nonNull(message.getSystemMessage())) {
            messageDto.setMessageInviteStatus(message.getSystemMessageStatus());
        }
        // Get hidden message flag
        if (message.getHidden().isHidden()) {
            messageDto.setHidden(true);
            messageDto.setText("");
        }

        logger.debug("end convertMessageToMessageDto : messageDto = {}", messageDto);
        return messageDto;
    }

    public static ChatDto convertChatToChatDto(Chat chat, String chatName) {
        return new ChatDto(chat.getChatId(), chatName, chat.getHidden());
    }

    public static FriendDto convertFriendToFriendDto(Friend friend) {
        logger.debug(" convertFriendToFriendDto friend {} ", friend);
        return new FriendDto(friend.getFriendName(), friend.getFriendStatus(), friend.getChatId());
    }

    public static GroupDto convertGroupToGroupsDto(Group group, long loggedUserId) {
        return convertGroupsToGroupsDto(Arrays.asList(group), loggedUserId).get(0);
    }

    public static List<GroupUserDto> convertUserToGroupUserDto(List<User> users, long adminId) {
        List<GroupUserDto> usersList = new ArrayList<>();
        for (User user : users) {
            boolean isAdmin = user.getUserId() == adminId;
            if (isAdmin) {
                usersList.add(0, new GroupUserDto(user.getUserId(), user.getUserFullName(), isAdmin));
            } else {
                usersList.add(new GroupUserDto(user.getUserId(), user.getUserFullName(), isAdmin));
            }
        }
        return usersList;
    }

    public static List<GroupDto> convertGroupsToGroupsDto(List<Group> groups, long loggedUserId) {
        logger.debug(" convertGroupsToGroupsDto groups size {} ", groups.size());
        List<GroupDto> groupsDtoList = new ArrayList<>();
        for (Group group : groups) {
            boolean isAdmin = loggedUserId == group.getAdminId();
            List<GroupUserDto> users = convertUserToGroupUserDto(group.getUsers(), group.getAdminId());

            groupsDtoList.add(new GroupDto(group.getGroupName(), group.getChatId(), users, isAdmin, group.getHidden()));
        }
        logger.debug(" convertGroupsToGroupsDto groups {} ", groupsDtoList);
        return groupsDtoList;
    }
}
