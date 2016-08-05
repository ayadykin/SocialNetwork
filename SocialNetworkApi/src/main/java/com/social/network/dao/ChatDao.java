package com.social.network.dao;

import java.util.Date;

import com.social.network.model.Chat;
import com.social.network.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
public interface ChatDao extends GenericDao<Chat, Long> {

    Chat getMessages(long chatId, User user, boolean readed, Date date);

}
