package com.social.network.domain.dao;

import com.social.network.domain.model.Chat;
import com.social.network.domain.model.User;
import com.social.network.domain.model.UserChat;

/**
 * Created by Yadykin Andrii Jul 12, 2016
 *
 */

public interface UserChatDao extends GenericDao<UserChat, Long>{
    UserChat findByChatAndUser(long chatId, long userId);
    
    boolean removeUserFromChat(Chat chat, User user);
}
