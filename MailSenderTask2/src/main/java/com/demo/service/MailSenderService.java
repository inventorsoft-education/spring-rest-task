package com.demo.service;

import java.util.List;
import com.demo.model.MailSender;

public interface MailSenderService {

	public void add(MailSender sender);
	
	public List<MailSender> getAll();
	
	public MailSender get(int id);
	
	public void remove(int id);
}
