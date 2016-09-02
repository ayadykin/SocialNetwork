package com.social.network.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.social.network.core.message.FriendAnswerMessage;
import com.social.network.core.message.text.MessageTextBuilder;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.services.FriendService;
import com.social.network.services.MessageService;
import com.social.network.services.RedisService;

public abstract class FriendTemplate {

    @Autowired
    protected FriendService friendService;
    @Autowired
    private MessageTextBuilder messageTextBuilder;
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected FriendAnswerMessage friendAnswerMessage;
    @Autowired
    private RedisService redisService;

    final public boolean friendAction(long userId, String template) {
        FriendModel friendModel = callService(userId);
        String messageText = createMessageText(template, friendModel.getUser().getUserFullName());
        Message message = createMessage(messageText, friendModel.getUser(), friendModel.getChat());
        return sendMessageToRedis(message);
    }

    public abstract FriendModel callService(long userId);

    public String createMessageText(String template, String param) {
        return messageTextBuilder.createOneParamMessage(template, param);
    }

    public abstract Message createMessage(String messageText, User publisher, Chat chat);

    public boolean sendMessageToRedis(Message message) {
        return redisService.sendMessageToRedis(message);
    }
}
