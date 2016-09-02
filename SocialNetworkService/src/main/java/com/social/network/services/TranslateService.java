package com.social.network.services;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

public interface TranslateService {
    /**
     * @param messageText
     * @param to
     * @return
     */
    String translateString(String messageText,  String to);
}
