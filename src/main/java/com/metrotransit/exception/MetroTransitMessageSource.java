package com.metrotransit.exception;

import com.metrotransit.utils.ErrorMsgEnum;

public interface MetroTransitMessageSource {

	String getMessage(ErrorMsgEnum msgKey);

}
