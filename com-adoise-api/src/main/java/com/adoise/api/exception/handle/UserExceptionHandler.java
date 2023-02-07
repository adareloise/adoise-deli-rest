package com.adoise.api.exception.handle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.adoise.library.exception.ExceptionResponse;
import com.adoise.library.exception.global.EmailExistsException;
import com.adoise.library.exception.global.EmailNotFoundException;

import java.util.Date;

/**
 * User exception handler.
 * <p>
 * Exception message is set here.
 */

@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * Email already exist exception.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Object> emailExistsException(Exception ex, WebRequest request) {
        
    	   ExceptionResponse exceptionResponse = new ExceptionResponse( 
    			   new Date(), 
   					ex.getMessage(), 
   					request.getDescription(false));
    	   
    	   
           return new ResponseEntity<Object>(exceptionResponse, HttpStatus.FOUND);

    }

    /**
     * Email not found exception.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Object>  emailNotFoundException(Exception ex, WebRequest request) {
    	
    	 ExceptionResponse exceptionResponse = new ExceptionResponse( 
  			   new Date(), 
 					ex.getMessage(), 
 					request.getDescription(false));
  	   
  	   
         return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);

    }
}
