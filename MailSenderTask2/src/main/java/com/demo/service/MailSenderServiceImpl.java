package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.MailSenderDAO;
import com.demo.model.MailSender;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	@Autowired
	private MailSenderDAO mailSenderDAO;
	
	@Override
	public List<MailSender> getAll() {
	 
		List<MailSender> list = null;
		
		try {
			list = mailSenderDAO.getSenders();
		} catch (Exception e) {		 
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public MailSender get(int id) {		 
		
		MailSender sender = mailSenderDAO.getMailSender(id);
		return sender;
	}

	@Override
	public void remove(int id) {

		try {
			mailSenderDAO.removeSender(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void add(MailSender sender) {
	
		try {			 
			mailSenderDAO.addSender(sender);
		} catch (Exception e) {			 
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "MailSenderServiceImpl [mailSenderDAO=" + mailSenderDAO + ", getAll()=" + getAll() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	


}
