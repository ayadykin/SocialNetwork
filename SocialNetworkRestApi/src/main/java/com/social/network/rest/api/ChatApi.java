package com.social.network.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.social.network.exceptions.chat.EmptyMessageException;
import com.social.network.redis.RedisMessageModel;
import com.social.network.rest.dto.chat.ChatDto;
import com.social.network.rest.dto.chat.EditMessageDto;
import com.social.network.rest.dto.chat.GetChatMessagesDto;
import com.social.network.rest.dto.chat.SendMessageDto;
import com.social.network.rest.facade.ChatServiceFacade;
import com.social.network.services.RedisService;
import com.social.network.utils.RestResponse;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@RestController
@RequestMapping(value = "/chat")
public class ChatApi {

    @Autowired
    private ChatServiceFacade chatFacade;
    @Autowired
    private RedisService redisService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<ChatDto> getChatsList() {
        return chatFacade.getChatsList();
    }

    @ResponseBody
    @RequestMapping(value = "/{chatId}", method = RequestMethod.GET)
    public ChatDto getChat(@PathVariable("chatId") long chatId) {
        return chatFacade.getChat(chatId);
    }

    @ResponseBody
    @RequestMapping(value = "/getMessages/{chatId}", method = RequestMethod.GET)
    public List<RedisMessageModel> getFilteredMessages(@PathVariable("chatId") long chatId) {
        return chatFacade.getChatMesasges(chatId);
    }

    @ResponseBody
    @RequestMapping(value = "/getMessages", method = RequestMethod.POST)
    public List<RedisMessageModel> getChatMessages(@RequestBody GetChatMessagesDto getChatMessagesDto) {
        return chatFacade.getChatMesasges(getChatMessagesDto.getChatId(), getChatMessagesDto.getDateFilter());
    }

    @ResponseBody
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public RestResponse sendMessageToChat(@RequestBody SendMessageDto sendMessageDto) {
        if (sendMessageDto.getMessage().isEmpty()) {
            throw new EmptyMessageException("Message must not be empty");
        }

        return new RestResponse().convert(
                () -> chatFacade.sendMessage(sendMessageDto.getMessage(), sendMessageDto.getChatId(), sendMessageDto.getPublicMessage()));
    }

    @ResponseBody
    @RequestMapping(value = "/editMessage", method = RequestMethod.POST)
    public RestResponse editMessage(@RequestBody EditMessageDto editMessageDto) {
        return new RestResponse().convert(() -> chatFacade.editMessage(editMessageDto.getMessageId(), editMessageDto.getMessage()));
    }

    @ResponseBody
    @RequestMapping(value = "/deleteMessage/{messageId}", method = RequestMethod.DELETE)
    public RestResponse deleteMessage(@PathVariable("messageId") long messageId) {
        return new RestResponse().convert(() -> chatFacade.deleteMessage(messageId));
    }

    @ResponseBody
    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public DeferredResult<RedisMessageModel> getRedisMessage() {
        return chatFacade.getRedisMessage();
    }
}
