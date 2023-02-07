package com.adoise.library.exception.global;

import org.springframework.dao.DataAccessException;

@SuppressWarnings("serial")
public class DataException extends DataAccessException{

	public DataException(String s) {
		super(s);
	}
		
	public DataException(String s, Throwable throwable) {      
		super(s, throwable);
	 }
	
}
