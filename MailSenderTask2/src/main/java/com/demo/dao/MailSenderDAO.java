package com.demo.dao;

import java.util.List;
import com.demo.model.MailSender;

public interface MailSenderDAO {

	public void addSender(MailSender sender) throws Exception;
	
	public List<MailSender> getSenders() throws Exception;
	
	public MailSender getMailSender(int id);
	
	public void removeSender(int id) throws Exception;
}
