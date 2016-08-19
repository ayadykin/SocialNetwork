package com.social.network.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.social.network.domain.model.enums.Period;
import com.social.network.facade.ChatServiceFacade;
import com.social.network.services.ChatService;
import com.social.network.services.UserService;

/**
 * Created by Yadykin Andrii May 18, 2016
 *
 */

@Controller
@RequestMapping(value = "/chat")
public class ChatController {

    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final static String CHATS_LIST_ATTRIBUTE = "chats_list";
    private final static String CHAT_MESSAGES_ATTRIBUTE = "chat_messages";
    private final static String USERID_ID_ATTRIBUTE = "loggedUserId";
    private static final String CHATS_LIST_VIEW_NAME = "chat/chatsList";
    @Autowired
    private ChatServiceFacade chatFacade;
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getChatsList() {
        ModelAndView mav = new ModelAndView(CHATS_LIST_VIEW_NAME);
        mav.addObject(CHATS_LIST_ATTRIBUTE, chatFacade.getChatsList());
        mav.addObject(USERID_ID_ATTRIBUTE, userService.getLoggedUserId());
        return mav;
    }

    @RequestMapping(value = "/{chatId}", method = RequestMethod.GET)
    public ModelAndView getFilteredMessages(@PathVariable("chatId") long chatId) {
        ModelAndView mav = new ModelAndView(CHATS_LIST_VIEW_NAME);
        mav.addObject(CHATS_LIST_ATTRIBUTE, chatService.getChatsList());
        mav.addObject(CHAT_MESSAGES_ATTRIBUTE, chatService.getChatMesasges(chatId, true, Period.ALL));
        return mav;
    }

}
