package com.social.network.core.message.text;

public interface MessageTextBuilder {

	String createOneParamMessage(String messageTemplate, String firstParam);

	String createTwoParamsMessage(String messageTemplate, String firstParam, String secondParam);

	String createThreeParamsMessage(String messageTemplate, String firstParam, String secondParam, String therdParam);
}
