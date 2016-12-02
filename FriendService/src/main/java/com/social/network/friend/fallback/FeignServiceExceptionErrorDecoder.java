package com.social.network.friend.fallback;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * Created by Yadykin Andrii Dec 2, 2016
 *
 */

@Slf4j
public class FeignServiceExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error(response.toString());
        if (response.status() == 400)
            throw new IllegalArgumentException("bad zone name");

         //throw new RuntimeException(methodKey + " -5 " + response);
         return decode(methodKey, response);
    }

}
