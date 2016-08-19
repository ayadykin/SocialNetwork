package com.social.network.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.dao.UserChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;
import com.social.network.domain.model.enums.FriendStatus;
import com.social.network.domain.model.enums.Period;
import com.social.network.exceptions.chat.ChatPermissionException;
import com.social.network.exceptions.chat.ChatRemovedException;
import com.social.network.services.ChatService;
import com.social.network.services.FriendService;
import com.social.network.services.MessageService;
import com.social.network.services.UserService;
import com.social.network.utils.Constants;
import com.social.network.validation.DaoValidation;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 */

@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    @Autowired
    private ChatDao chatDao;
    @Autowired
    private UserChatDao userChatDao;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private MessageService messageService;

    @Override
    @Transactional(readOnly = true)
    public Set<UserChat> getChatsList() {
        User loggedUser = userService.getLoggedUserEntity();
        logger.debug("->getChatsList for user : {}", loggedUser.getUserId());

        return loggedUser.getUserChat();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getChatMesasges(long chatId, boolean readed, Period period) {
        logger.debug("getChatMesasges : chatId = {}, period = {}", chatId, period);
        User loggedUser = userService.getLoggedUserEntity();

        // Validation
        getChatMesasgesValidate(chatId, loggedUser.getUserId());

        // Get list of messages
        Date date = getDate(period);

        return chatDao.getMessages(chatId, loggedUser, readed, date);
    }

    @Override
    @Transactional
    public Message sendMessage(String messageText, long chatId) {
        logger.debug("sendMessage messageText : {}, to chatId : {} ", messageText, chatId);
        User loggedUser = userService.getLoggedUserEntity();

        // Validation
        sendMessageValidate(chatId, loggedUser.getUserId());

        Chat chat = DaoValidation.chatExistValidation(chatDao, chatId);

        Message message = messageService.createMessage(messageText, loggedUser, chat);

        return message;

    }

    private Date getDate(Period period) {
        logger.debug("getFiltered  period = {}", period);

        // Filter messages by period
        DateTime currentDate = new DateTime();
        Date date = null;
        try {
            switch (period) {
            case WEEK:
                date = Constants.dbDateFormat.parse(Constants.jodaFormat.print(currentDate.minusDays(7)));
                break;
            case DAY:
                date = Constants.dbDateFormat.parse(Constants.jodaFormat.print(currentDate.minusDays(1)));
                break;
            case MONTH:
                date = Constants.dbDateFormat.parse(Constants.jodaFormat.print(currentDate.minusMonths(1)));
                break;
            default:
                break;

            }
        } catch (ParseException e) {
            logger.error("getFilteredMessages error : {}", e.getMessage());
        }
        return date;
    }

    private void sendMessageValidate(long chatId, long userId) {
        UserChat userChat = userChatDao.findByChatAndUser(chatId, userId);
        validateChat(userChat);
        validateHiddenChat(userChat);
        validateFriend(chatId);
    }

    private void getChatMesasgesValidate(long chatId, long userId) {
        UserChat userChat = userChatDao.findByChatAndUser(chatId, userId);
        validateChat(userChat);
    }

    private void validateFriend(long chatId) {

        Chat chat = DaoValidation.chatExistValidation(chatDao, chatId);
        Iterator<User> iterator = chat.getUsers().iterator();
        User inviterUser = iterator.next();
        User inviteeUser = iterator.next();
        try {
            friendService.validateFriendByStatus(inviterUser, inviteeUser, FriendStatus.ACCEPTED);
        } catch (RuntimeException e) {
            throw new ChatPermissionException("Friend not accepted your invitation");
        }

    }

    private void validateChat(UserChat userChat) {
        if (Objects.isNull(userChat)) {
            throw new ChatPermissionException("You haven't permisions for this chat");
        }

    }

    private void validateHiddenChat(UserChat userChat) {
        if (userChat.getChat().getHidden()) {
            throw new ChatRemovedException("Chat was removed");
        }
    }
}
