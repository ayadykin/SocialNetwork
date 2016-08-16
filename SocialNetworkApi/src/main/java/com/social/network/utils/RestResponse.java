package com.social.network.utils;

/**
 * Created by Yadykin Andrii Aug 4, 2016
 *
 */

public class RestResponse {

    private boolean response;

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public RestResponse convert(Result result) {
        Boolean success = result.apply();
        if (success) {
            response = true;
        } else {
            response = false;
        }
        return this;
    }

    public static interface Result {
        Boolean apply();
    }
}
