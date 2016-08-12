package com.social.network.facade;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.dto.GroupDto;
import com.social.network.dto.MessageDto;
import com.social.network.model.Group;
import com.social.network.model.Message;
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

    @Autowired
    private GroupService groupService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    @Transactional
    public GroupDto createGroup(String name, String[] userList) {
        long userId = userService.getLoggedUserId();

        Group group = groupService.createGroup(name, userList);
        
        /*Set<Message> messages = group.getChat().getMessages();

        // Send messages to redis
        for (Message message : messages) {
            sendMessageToRedis(message, userId);
        }*/
        return EntityToDtoMapper.convertGroupToGroupsDto(group, userId);
    }

    @Transactional
    public Set<GroupDto> getGroups() {
        long userId = userService.getLoggedUserId();
        return EntityToDtoMapper.convertGroupsToGroupsDto(groupService.getGroups(), userId);
    }

    @Transactional
    public GroupDto getGroup(long groupId) {
        long userId = userService.getLoggedUserId();

        Group group = groupService.getGroup(groupId);

        return EntityToDtoMapper.convertGroupToGroupsDto(group, userId);
    }

    @Transactional
    public boolean addUserToGroup(long groupId, long userId) {

        Message message = groupService.addUserToGroup(groupId, userId);
        sendMessageToRedis(message, userId);
        return true;
    }

    @Transactional
    public boolean deleteUserFromGroup(long groupId, long userId) {

        Message message = groupService.deleteUserFromGroup(groupId, userId);
        sendMessageToRedis(message, userId);
        return true;
    }

    @Transactional
    public boolean leaveGroup(long groupId) {
        long userId = userService.getLoggedUserId();
        Message message = groupService.leaveGroup(groupId);
        sendMessageToRedis(message, userId);

        return true;
    }

    @Transactional
    public boolean deleteGroup(long groupId) {
        long userId = userService.getLoggedUserId();
        Message message = groupService.deleteGroup(groupId);
        sendMessageToRedis(message, userId);
        return true;
    }

    private boolean sendMessageToRedis(Message message, long userId) {
        MessageDto messageDto = EntityToDtoMapper.convertMessageToMessageDto(message, userId);
        return redisService.sendMessageToRedis(messageDto);
    }
}
