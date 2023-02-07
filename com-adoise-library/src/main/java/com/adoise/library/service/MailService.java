package com.adoise.library.service;

import javax.mail.MessagingException;

public interface MailService {

	void sendMail(String from, String to, String cc, String subject, String text) throws MessagingException;
	
}
