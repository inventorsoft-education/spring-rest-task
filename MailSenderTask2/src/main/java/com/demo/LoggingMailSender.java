package com.demo;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.demo.model.MailSender;
import com.demo.service.MailSenderService;

@Component 
public class LoggingMailSender implements CommandLineRunner {

	@Autowired
	private MailSenderService mailSenderService;	
	
	@Autowired
	private SmtpMailSender smtpMailSender;
	
	
	@Override
	public void run(String... args) throws Exception {
	
		try(Scanner scanner = new Scanner(System.in)) {
			
			System.out.println(".....new email.......");
			
			System.out.println("enter recipient: ");
			String recipient = scanner.nextLine();
			
			System.out.println("enter subject: ");
			String subject = scanner.nextLine();
			
			System.out.println("enter body: ");
			String body = scanner.nextLine();
			
			mailSenderService.add(new MailSender(recipient, subject, body));
			
			System.out.println("email added");
			
			smtpMailSender.send(recipient, subject, body);
		}
	
	
	}
}
