package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;


import com.demo.service.MailSenderService;

@EnableAsync
@SpringBootApplication
public class MailSenderTask2Application {

	public static void main(String[] args) throws MailException, InterruptedException {	 
		SpringApplication.run(MailSenderTask2Application.class, args);
	 
	}
}
