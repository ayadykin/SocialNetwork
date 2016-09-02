package com.social.network.services;

import com.social.network.domain.model.Message;
import com.social.network.redis.RedisMessageModel;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */

public interface RedisService {
    /**
     * @return
     */
    RedisMessageModel getMessage();

    /**
     * @param message
     * @param userId
     * @return
     */
    boolean sendMessageToRedis(Message message);

}
