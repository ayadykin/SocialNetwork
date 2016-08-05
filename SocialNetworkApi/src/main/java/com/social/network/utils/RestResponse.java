package com.social.network.utils;

/**
 * Created by Yadykin Andrii Aug 4, 2016
 *
 */

public class RestResponse {

    private boolean response;

    public RestResponse(boolean result) {
        response = result;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

}
