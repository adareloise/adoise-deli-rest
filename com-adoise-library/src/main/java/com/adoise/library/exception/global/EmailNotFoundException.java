package com.adoise.library.exception.global;

import com.adoise.library.exception.RestApiException;

@SuppressWarnings("serial")
public class EmailNotFoundException extends RestApiException {

    public EmailNotFoundException(String s) {
        super(s);
    }

    public EmailNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
