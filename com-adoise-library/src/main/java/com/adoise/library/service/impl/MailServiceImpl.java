package com.adoise.library.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.adoise.library.service.MailService;


@Component
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendMail(String from, String to, String cc, String subject, String text) throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setCc(cc);
		helper.setFrom(from);
		helper.setSubject(subject);
		helper.setText(text, true);
		javaMailSender.send(message);
		
	}
}
