package com.social.network.friend.fallback;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

/**
 * Created by Yadykin Andrii Dec 2, 2016
 *
 */

@Log4j2
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
