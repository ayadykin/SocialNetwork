package com.social.network.api;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.social.network.domain.model.enums.Period;
import com.social.network.dto.MessageDto;
import com.social.network.exceptions.chat.EmptyMessageException;
import com.social.network.facade.ChatServiceFacade;
import com.social.network.services.RedisService;
import com.social.network.utils.ResultToResponseWrapper;

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
    @RequestMapping(method = RequestMethod.POST)
    public List<MessageDto> getChatMessages(@RequestParam("chatId") long chatId, @RequestParam("filter") Period period) {
        return chatFacade.getChatMesasges(chatId, period);
    }

    @ResponseBody
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public String sendMessageToChat(@RequestParam() String message, @RequestParam("chatId") long chatId) {
        if (message.isEmpty()) {
            throw new EmptyMessageException("Message must not be empty");
        }

        return ResultToResponseWrapper.convert(() -> chatFacade.sendMessage(message, chatId));
    }

    @ResponseBody
    @RequestMapping(value = "/editMessage", method = RequestMethod.POST)
    public String editMessage(@RequestParam("messageId") long messageId, @RequestParam("message") String message) {
        return ResultToResponseWrapper.convert(() -> chatFacade.editMessage(messageId, message));
    }

    @ResponseBody
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
    public String deleteMessage(@RequestParam("messageId") long messageId, @RequestParam("messageChatId") long messageChatId) {
        return ResultToResponseWrapper.convert(() -> chatFacade.deleteMessage(messageId));
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
