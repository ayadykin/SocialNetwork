package com.social.network.rest.facade;

import static com.social.network.utils.Constants.ADD_USER_TO_GROUP_MESSAGE;
import static com.social.network.utils.Constants.CREATE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.CREATE_PUBLIC_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_GROUP_MESSAGE;
import static com.social.network.utils.Constants.DELETE_USER_FROM_GROUP_MESSAGE;
import static com.social.network.utils.Constants.LEAVE_GROUP_MESSAGE;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.core.GroupModel;
import com.social.network.core.message.FriendsNotification;
import com.social.network.core.message.text.MessageTextBuilder;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.SystemMessageStatus;
import com.social.network.rest.dto.group.GroupDto;
import com.social.network.rest.dto.group.GroupUserDto;
import com.social.network.rest.utils.EntityToDtoMapper;
import com.social.network.services.GroupService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;
import com.social.network.services.UserService;

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
    private MessageTextBuilder messageTextBuilder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private FriendsNotification friendsNotification;

    @Transactional
    public GroupDto createGroup(String name, String[] userList, boolean publicGroup) {
        long userId = userService.getLoggedUserId();

        Group group = groupService.createGroup(name, userList);
        Chat chat = group.getChat();
        ;

        // Create messages
        for (UserChat user : chat.getUserChat()) {
            String messageText = null;
            if (user.getUserId() == userId) {
                messageText = messageTextBuilder.createOneParamMessage(CREATE_GROUP_MESSAGE, user.getUser().getUserFullName());
            } else {
                messageText = messageTextBuilder.createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE, group.getAdmin().getUserFullName(),
                        user.getUser().getUserFullName());
            }
            sendMessageToRedis(messageService.createSystemMessage(messageText, group.getAdmin(), chat, SystemMessageStatus.SYSTEM));
        }

        // Public group notification
        if (publicGroup) {

            String messageText = messageTextBuilder.createTwoParamsMessage(CREATE_PUBLIC_GROUP_MESSAGE, group.getAdmin().getUserFullName(),
                    name);
            friendsNotification.notificate(messageText, group.getAdmin());
        }

        return EntityToDtoMapper.convertGroupToGroupsDto(group, userId, false);
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

        String messageText = messageTextBuilder.createTwoParamsMessage(ADD_USER_TO_GROUP_MESSAGE, invitedUser.getUserFullName(),
                groupModel.getLoggedUser().getFirstName());

        Message message = messageService.createSystemMessage(messageText, groupModel.getLoggedUser(), chat, SystemMessageStatus.SYSTEM);

        sendMessageToRedis(message);
        return new GroupUserDto(invitedUser.getUserId(), invitedUser.getUserFullName(), false);
    }

    @Transactional
    public GroupUserDto deleteUserFromGroup(long groupId, long userId) {

        GroupModel groupModel = groupService.deleteUserFromGroup(groupId, userId);

        Chat chat = groupModel.getGroup().getChat();
        User removedUser = groupModel.getInvitedUser();

        // Create message
        String messageText = messageTextBuilder.createTwoParamsMessage(DELETE_USER_FROM_GROUP_MESSAGE, removedUser.getUserFullName(),
                groupModel.getLoggedUser().getFirstName());
        Message message = messageService.createSystemMessage(messageText, groupModel.getLoggedUser(), chat, SystemMessageStatus.SYSTEM);

        sendMessageToRedis(message);
        return new GroupUserDto(removedUser.getUserId(), removedUser.getUserFullName(), false);
    }

    @Transactional
    public boolean leaveGroup(long groupId) {
        long userId = userService.getLoggedUserId();

        GroupModel groupModel = groupService.leaveGroup(groupId);

        // Create message
        String messageText = messageTextBuilder.createOneParamMessage(LEAVE_GROUP_MESSAGE, groupModel.getLoggedUser().getUserFullName());

        Message message = messageService.createSystemMessage(messageText, groupModel.getLoggedUser(), groupModel.getGroup().getChat(),
                SystemMessageStatus.SYSTEM);

        sendMessageToRedis(message);

        return true;
    }

    @Transactional
    public GroupDto deleteGroup(long groupId) {
        long userId = userService.getLoggedUserId();
        GroupModel groupModel = groupService.deleteGroup(groupId);

        // Create message
        String messageText = messageTextBuilder.createOneParamMessage(DELETE_GROUP_MESSAGE, groupModel.getLoggedUser().getUserFullName());

        Message message = messageService.createSystemMessage(messageText, groupModel.getLoggedUser(), groupModel.getGroup().getChat(),
                SystemMessageStatus.SYSTEM);

        sendMessageToRedis(message);
        return EntityToDtoMapper.convertGroupToGroupsDto(groupModel.getGroup(), userId, false);
    }

    private boolean sendMessageToRedis(Message message) {
        return redisService.sendMessageToRedis(message);
    }
}
