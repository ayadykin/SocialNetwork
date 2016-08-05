package com.social.network.utils;

import static com.social.network.utils.Constants.ERROR_RESPONSE;
import static com.social.network.utils.Constants.SUCCESS_RESPONSE;

/**
 * Created by Yadykin Andrii May 24, 2016
 *
 */

public class ResultToResponseWrapper {

    public static String convert(Result result) {
        Boolean success = result.apply();
        if (success) {
            return SUCCESS_RESPONSE;
        } else {
            return ERROR_RESPONSE;
        }
    }

    public static interface Result {
        Boolean apply();
    }
}
