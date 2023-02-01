package com.adoise.library.exception;


/**
 * Base class exception for rest api endpoints.
 */

@SuppressWarnings("serial")
public class RestApiException extends AuthenticationException {

    public RestApiException(String s) {
        super(s);
    }

    public RestApiException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
