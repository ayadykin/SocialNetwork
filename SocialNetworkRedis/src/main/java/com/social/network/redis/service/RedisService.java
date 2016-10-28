package com.social.network.redis.service;

import com.social.network.redis.model.RedisMessage;

/**
 * Created by Yadykin Andrii May 27, 2016
 *
 */

public interface RedisService {
    /**
     * @return
     */
    RedisMessage getMessage();

    /**
     * @param message
     * @param userId
     * @return
     */
    boolean sendMessageToRedis(String message, long chatId, String publisherNamme);

}
