package com.social.network.redis;

/**
 * Created by Yadykin Andrii Jul 25, 2016
 *
 */

public interface RedisMessageObserver {
    boolean updateMessageStatus(long userId, long messageId);
}
