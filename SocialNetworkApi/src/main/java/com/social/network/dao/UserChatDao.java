package com.social.network.dao;

import com.social.network.model.Chat;
import com.social.network.model.User;
import com.social.network.model.UserChat;

/**
 * Created by Yadykin Andrii Jul 12, 2016
 *
 */

public interface UserChatDao {
    UserChat findByChatAndUser(long chatId, long userId);
    
    boolean removeUserFromChat(Chat chat, User user);
}
