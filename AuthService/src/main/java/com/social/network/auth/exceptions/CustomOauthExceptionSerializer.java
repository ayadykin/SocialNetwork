package com.social.network.auth.exceptions;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Created by Yadykin Andrii Dec 2, 2016
 *
 */
@Slf4j
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException>{
    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        log.debug("---------");
        gen.writeStartObject();
        gen.writeStringField("custom_error", value.getOAuth2ErrorCode());
        gen.writeStringField("custom_error_description", value.getMessage());
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}

