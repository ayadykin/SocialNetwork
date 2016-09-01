package com.social.network.domain.dao;

import java.util.Date;
import java.util.List;

import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
public interface MessageDao extends GenericDao<Message, Long> {
    
    List<Message> getMessages(long chatId, User user, boolean readed, Date date);
}
