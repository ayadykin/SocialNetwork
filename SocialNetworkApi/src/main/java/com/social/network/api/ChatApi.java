package com.social.network.api;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.social.network.domain.model.enums.Period;
import com.social.network.dto.MessageDto;
import com.social.network.dto.chat.ChatDto;
import com.social.network.dto.chat.EditMessageDto;
import com.social.network.dto.chat.SendMessageDto;
import com.social.network.exceptions.chat.EmptyMessageException;
import com.social.network.facade.ChatServiceFacade;
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
    public List<MessageDto> getFilteredMessages(@PathVariable("chatId") long chatId) {
        return chatFacade.getChatMesasges(chatId, Period.ALL);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public List<MessageDto> getChatMessages(@RequestParam("chatId") long chatId, @RequestParam("filter") Period period) {
        return chatFacade.getChatMesasges(chatId, period);
    }

    @ResponseBody
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public RestResponse sendMessageToChat(@RequestBody SendMessageDto sendMessageDto) {
        if (sendMessageDto.getMessage().isEmpty()) {
            throw new EmptyMessageException("Message must not be empty");
        }

        return new RestResponse().convert(() -> chatFacade.sendMessage(sendMessageDto.getMessage(), sendMessageDto.getChatId()));
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
    @RequestMapping(value = "/getMessage/{chatId}", method = RequestMethod.GET)
    public DeferredResult<MessageDto> getMessage(@PathVariable("chatId") long chatId) {
        final DeferredResult<MessageDto> deferredResult = new DeferredResult<MessageDto>(null, "");

        MessageDto message = redisService.getMessage();
        if (Objects.nonNull(message)) {
            deferredResult.setResult(message);
        }

        return deferredResult;
    }
}
