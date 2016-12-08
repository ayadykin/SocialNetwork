package com.social.network.message.service;

/**
 * Created by Yadykin Andrii Oct 13, 2016
 *
 */

public interface CounterService {
    long getNextSequence(String counterName);
}
