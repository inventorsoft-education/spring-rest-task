package com.demo.model;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat; 

public class MailSender {

	private int id;
	private String recipient;
	private String subject;
	private String body;	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date date;
	
	public MailSender(int id, String recipient, String subject, String body) {	
		this.id = id;
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
		this.date = new Date();
	}
	
	public MailSender(String recipient, String subject, String body) {		
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
		this.date = new Date();
	}	
	

	public MailSender(int id) {		 
		this.id = id;
	}



	public MailSender() {
		
	}	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}	
	  

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailSender other = (MailSender) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
