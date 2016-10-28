package com.social.network.rest.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.social.network.exceptions.chat.EmptyMessageException;
import com.social.network.redis.model.RedisMessage;
import com.social.network.rest.dto.chat.ChatDto;
import com.social.network.rest.dto.chat.EditMessageDto;
import com.social.network.rest.dto.chat.SendMessageDto;
import com.social.network.rest.facade.ChatServiceFacade;
import com.social.network.utils.RestResponse;

import static com.social.network.rest.utils.Constants.CHAT_PARAM;
import static com.social.network.rest.utils.Constants.CHAT_PATH;

/**
 * Created by Yadykin Andrii Jul 22, 2016
 *
 */

@RestController
@RequestMapping(value = CHAT_PATH)
public class ChatApi {

	private static final Logger logger = LoggerFactory.getLogger(ChatApi.class);

	@Autowired
	private ChatServiceFacade chatFacade;

	@RequestMapping(method = RequestMethod.GET)
	public List<ChatDto> getChatsList() {
		logger.info(" getChatsList ");
		return chatFacade.getChatsList();
	}

	@RequestMapping(value = CHAT_PARAM, method = RequestMethod.GET)
	public ChatDto getChat(@PathVariable("chatId") long chatId) {
		logger.info(" getChat chatId : {}", chatId);
		return chatFacade.getChat(chatId);
	}

	@RequestMapping(value = "/getMessages" + CHAT_PARAM, method = RequestMethod.GET)
	public List<RedisMessage> getMessages(@PathVariable("chatId") long chatId) {
		return chatFacade.getChatMesasges(chatId);
	}

	/*@RequestMapping(value = "/getMessages", method = RequestMethod.POST)
	public List<RedisMessageModel> getFilteredChatMessages(@RequestBody GetChatMessagesDto getChatMessagesDto) {
		return chatFacade.getChatMesasges(getChatMessagesDto.getChatId(), getChatMessagesDto.getDateFilter());
	}*/

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public RestResponse sendMessageToChat(@RequestBody SendMessageDto sendMessageDto) {
		if (StringUtils.isEmpty(sendMessageDto.getMessage())) {
			logger.error(" sendMessageToChat sendMessageDto : {}", sendMessageDto);
			throw new EmptyMessageException("Message must not be empty");
		}

		return new RestResponse().convert(() -> chatFacade.sendMessage(sendMessageDto.getMessage(),
				sendMessageDto.getChatId(), sendMessageDto.getPublicMessage()));
	}

	@RequestMapping(value = "/editMessage", method = RequestMethod.POST)
	public RestResponse editMessage(@RequestBody EditMessageDto editMessageDto) {
		return new RestResponse()
				.convert(() -> chatFacade.editMessage(editMessageDto.getMessageId(), editMessageDto.getMessage()));
	}

	@RequestMapping(value = "/deleteMessage/{messageId}", method = RequestMethod.DELETE)
	public RestResponse deleteMessage(@PathVariable("messageId") long messageId) {
		return new RestResponse().convert(() -> chatFacade.deleteMessage(messageId));
	}

	@RequestMapping(value = "/getMessage", method = RequestMethod.GET)
	public DeferredResult<RedisMessage> getRedisMessage() {
		return chatFacade.getRedisMessage();
	}
}
