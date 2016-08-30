package com.social.network.core.message.text.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.social.network.core.message.text.MessageTextBuilder;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

@Component
public class MessageTextBuilderImpl implements MessageTextBuilder {

	@Autowired
	private MessageSource messageSource;

	public String createOneParamMessage(String messageTemplate, String firstParam) {
		String[] arg = new String[] { firstParam };
		return buildMessage(messageTemplate, arg);
	}

	public String createTwoParamsMessage(String messageTemplate, String firstParam, String secondParam) {
		String[] arg = new String[] { firstParam, secondParam };
		return buildMessage(messageTemplate, arg);
	}

	private String buildMessage(String messageTemplate, String[] arg) {
		return messageSource.getMessage(messageTemplate, arg, Locale.getDefault());
	}
}
