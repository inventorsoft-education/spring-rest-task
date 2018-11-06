package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.demo.model.MailSender;
import com.demo.service.MailSenderService;

@RestController
public class MailSenderController {

	@Autowired
	private MailSenderService mailSenderService;
	
	@RequestMapping("/list")
	public List<MailSender> getSenders() {
		
		List<MailSender> senders = mailSenderService.getAll();		
		return senders;
	}
	
	@RequestMapping("/senders/{senderId}")
	public MailSender getSender(@PathVariable int senderId) {		
		
		return mailSenderService.get(senderId);
	}
	
	@RequestMapping(value = "/senders/{senderId}", method = RequestMethod.DELETE)
	public void deleteSender(@PathVariable int senderId) {
		
		mailSenderService.remove(senderId);
	}
}
