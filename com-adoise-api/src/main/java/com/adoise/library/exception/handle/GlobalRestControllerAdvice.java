package com.adoise.library.exception.handle;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.adoise.library.exception.ExceptionResponse;
import com.adoise.library.exception.global.PatchOperationNotSupported;

import java.util.Date;

/**
 * Global exceptions handler.
 * <p>
 * Exception message is set here instead of passing it through constructor.
 */

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    /**
     * Patch operation not supported exception.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(PatchOperationNotSupported.class)
    public ResponseEntity<Object> patchOperationNotSupported(Exception ex, WebRequest request) {
    	
        ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));

        return new ResponseEntity<Object>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }
}