package com.adoise.library.exception.global;

import com.adoise.library.exception.RestApiException;

@SuppressWarnings("serial")
public class PatchOperationNotSupported extends RestApiException {

    public PatchOperationNotSupported(String s) {
        super(s);
    }

    public PatchOperationNotSupported(String s, Throwable throwable) {
        super(s, throwable);
    }
}