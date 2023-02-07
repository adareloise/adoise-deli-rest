package com.adoise.library.exception.global;

import com.adoise.library.exception.RestApiException;

@SuppressWarnings("serial")
public class UserNotFoundException extends RestApiException {

    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}