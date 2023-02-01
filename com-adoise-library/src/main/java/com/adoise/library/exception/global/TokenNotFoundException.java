package com.adoise.library.exception.global;

import com.adoise.library.exception.RestApiException;

@SuppressWarnings("serial")
public class TokenNotFoundException extends RestApiException {

    public TokenNotFoundException(String s) {
        super(s);
    }

    public TokenNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
