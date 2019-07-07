package com.metrotransit.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.metrotransit.utils.ErrorMsgEnum;

@Component
public class MetroTransitErrorMessageSourceImpl implements MetroTransitMessageSource{

	
	@Autowired
	private MessageSource messageSource;

	@Override
	public String getMessage(ErrorMsgEnum msgKey) {
		Locale locale = new Locale("en", "US");
		return messageSource.getMessage(msgKey.toString(), null, locale);
	}

}
