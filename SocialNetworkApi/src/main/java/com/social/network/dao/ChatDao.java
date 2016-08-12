package com.social.network.dao;

import java.util.Date;
import java.util.List;

import com.social.network.model.Chat;
import com.social.network.model.Message;
import com.social.network.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
public interface ChatDao extends GenericDao<Chat, Long> {

    List<Message> getMessages(long chatId, User user, boolean readed, Date date);

}
