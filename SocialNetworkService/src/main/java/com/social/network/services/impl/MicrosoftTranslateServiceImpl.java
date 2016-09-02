package com.social.network.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.social.network.services.TranslateService;

/**
 * Created by Yadykin Andrii Jul 21, 2016
 *
 */

@Service
public class MicrosoftTranslateServiceImpl implements TranslateService {

    private static final Logger logger = LoggerFactory.getLogger(MicrosoftTranslateServiceImpl.class);

    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;

    @Override
    public String translateString(String messageText, String to) {
        logger.debug(" translateString messageText : {} ", messageText);
        Translate.setClientId(clientId);
        Translate.setClientSecret(clientSecret);
        try {
            return Translate.execute(messageText, getLanguage(to));
        } catch (Exception e) {
            logger.error(" microsoftTranslate {}", e);
            return "";
        }
    }

    private Language getLanguage(String locale) {

        Language language;
        switch (locale) {
        case "ru":
            language = Language.RUSSIAN;
            break;
        case "fr":
            language = Language.FRENCH;
            break;
        default:
            language = Language.ENGLISH;
            break;
        }
        return language;
    }
}
