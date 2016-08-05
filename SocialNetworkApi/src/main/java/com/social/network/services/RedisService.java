package com.social.network.services;

import com.social.network.dto.MessageDto;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */

public interface RedisService {
    /**
     * @return
     */
    MessageDto getMessage();

    /**
     * @param message
     * @param userId
     * @return
     */
    boolean sendMessageToRedis(MessageDto message);

}
