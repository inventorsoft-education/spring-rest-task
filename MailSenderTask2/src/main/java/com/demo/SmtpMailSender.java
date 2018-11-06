package com.demo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {
	
	private static final String CRON = "*/10 * * * * *";
	@Autowired
	private JavaMailSender javaMailSender;	
	
	@Scheduled(cron = CRON)
	public void send(String to, String subject, String body) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		helper = new MimeMessageHelper(message, true);  
													    
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true);  
		  
		
		javaMailSender.send(message);
		
		
	}

}
