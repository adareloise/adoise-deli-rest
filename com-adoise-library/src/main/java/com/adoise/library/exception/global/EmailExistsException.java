package com.adoise.library.exception.global;

import com.adoise.library.exception.RestApiException;

@SuppressWarnings("serial")
public class EmailExistsException extends RestApiException {

    public EmailExistsException(String s) {
        super(s);
    }

    public EmailExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
