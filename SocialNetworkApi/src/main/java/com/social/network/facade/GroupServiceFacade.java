package com.social.network.facade;

import static com.social.network.utils.Constants.ADD_USER_TO_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_USER_FROM_GROUP_MESSAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.GroupModel;
import com.social.network.core.message.builder.system.impl.GroupMessage;
import com.social.network.core.message.text.impl.MessageTextBuilderImpl;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.dto.GroupDto;
import com.social.network.dto.MessageDto;
import com.social.network.dto.group.GroupUserDto;
import com.social.network.services.GroupService;
import com.social.network.services.RedisService;
import com.social.network.services.UserService;
import com.social.network.utils.EntityToDtoMapper;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@Service
public class GroupServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceFacade.class);

    @Autowired
    private GroupService groupService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageTextBuilderImpl messageBuilder;
    @Autowired
    private GroupMessage groupMessageBuilder;

    @Transactional
    public GroupDto createGroup(String name, String[] userList) {
        long userId = userService.getLoggedUserId();

        Group group = groupService.createGroup(name, userList);

        Set<Message> messages = group.getChat().getMessages();

        // Send messages to redis
        for (Message message : messages) {
            logger.debug(" createGroup  message {} ", message);
            sendMessageToRedis(message, userId);
        }
        return EntityToDtoMapper.convertGroupToGroupsDto(group, userId, false);
    }

    @Transactional
    public List<GroupUserDto> getFriendsNotInGroup(long groupId) {
        List<User> users = groupService.getFriendsNotInGroup(groupId);
        // Fill FriendsDto list
        List<GroupUserDto> friendDtoList = new ArrayList<>();
        for (User user : users) {
            friendDtoList.add(new GroupUserDto(user.getUserId(), user.getUserFullName(), false));
        }
        return friendDtoList;
    }

    @Transactional
    public Set<GroupDto> getGroups() {
        long userId = userService.getLoggedUserId();
        return EntityToDtoMapper.convertGroupsToGroupsDto(groupService.getGroups(), userId, false);
    }

    @Transactional
    public GroupDto getGroup(long groupId) {
        long userId = userService.getLoggedUserId();

        Group group = groupService.getGroup(groupId);

        return EntityToDtoMapper.convertGroupToGroupsDto(group, userId, true);
    }

    @Transactional
    public GroupUserDto addUserToGroup(long groupId, long userId) {

        GroupModel groupModel = groupService.addUserToGroup(groupId, userId);

        Chat chat = groupModel.getGroup().getChat();
        User invitedUser = groupModel.getInvitedUser();

        // Create message
        Message message = messageBuilder.setMessageStrategyBuilder(groupMessageBuilder).createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE,
                invitedUser, groupModel.getLoggedUser(), chat);

        sendMessageToRedis(message, userId);
        return new GroupUserDto(invitedUser.getUserId(), invitedUser.getUserFullName(), false);
    }

    @Transactional
    public GroupUserDto deleteUserFromGroup(long groupId, long userId) {

        GroupModel groupModel = groupService.deleteUserFromGroup(groupId, userId);

        Chat chat = groupModel.getGroup().getChat();
        User removedUser = groupModel.getInvitedUser();

        // Create message
        Message message = messageBuilder.setMessageStrategyBuilder(groupMessageBuilder).createTwoParamsMessage(DELETE_USER_FROM_GROUP_MESSAGE,
                removedUser, groupModel.getLoggedUser(), chat);

        sendMessageToRedis(message, userId);
        return new GroupUserDto(removedUser.getUserId(), removedUser.getUserFullName(), false);
    }

    @Transactional
    public boolean leaveGroup(long groupId) {
        long userId = userService.getLoggedUserId();
        Message message = groupService.leaveGroup(groupId);
        sendMessageToRedis(message, userId);

        return true;
    }

    @Transactional
    public GroupDto deleteGroup(long groupId) {
        long userId = userService.getLoggedUserId();
        GroupModel groupModel = groupService.deleteGroup(groupId);

        // Create message
        Message message = messageBuilder.setMessageStrategyBuilder(groupMessageBuilder).createOneParamMessage(DELETE_GROUP_MESSAGE,
                groupModel.getLoggedUser(), groupModel.getGroup().getChat());
        sendMessageToRedis(message, userId);
        return EntityToDtoMapper.convertGroupToGroupsDto(groupModel.getGroup(), userId, false);
    }

    private boolean sendMessageToRedis(Message message, long userId) {
        MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message, userId);
        return redisService.sendMessageToRedis(messageDto);
    }
}
