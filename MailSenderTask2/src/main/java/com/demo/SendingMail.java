package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.demo.service.MailSenderService;

@Component
public class SendingMail {

	
	@Autowired
	private MailSenderService mailSenderService;
	
	
	@Async
	public void sendEmail(JavaMailSender javaMailSender, int id)
			throws MailException, InterruptedException {

		System.out.println("Sleeping now...");
		Thread.sleep(10000);
		System.out.println("Sending email...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo((String)mailSenderService.get(id).getRecipient());
		mail.setSubject((String)mailSenderService.get(id).getSubject());
		mail.setText((String)mailSenderService.get(id).getBody());
		javaMailSender.send(mail);

		System.out.println("Email Sent!");
	}

}

