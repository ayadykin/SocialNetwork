package com.social.network.services;

import java.util.Locale;

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
    String translateString(String messageText, Locale to);
}
