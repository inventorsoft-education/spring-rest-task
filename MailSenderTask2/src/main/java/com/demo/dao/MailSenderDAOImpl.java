package com.demo.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;
import com.demo.model.MailSender;

import java.io.File;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Repository
public class MailSenderDAOImpl implements MailSenderDAO {

	private List<MailSender> senders = null;
	
	@PostConstruct
	public void loadSenders() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Resource resource = new ClassPathResource("list.json");

		File file = resource.getFile();
		senders = objectMapper.readValue(file, new TypeReference<List<MailSender>>() {});
		
		System.out.println("loadSenders()........");
	}
	
	@Override
	public List<MailSender> getSenders() throws Exception {	 
			
		System.out.println("getSenders().....");
		return senders;
	}
	
	@Override
	public MailSender getMailSender(int id) {
		 
		int index = senders.indexOf(new MailSender(id));
		MailSender sender = senders.get(index);
		
		return sender;
	}


	@Override
	public void removeSender(int id) throws Exception {
				
		senders.remove(getMailSender(id));	
		saveAll();
	}
	
	@Override
	public void addSender(MailSender sender) throws Exception {

		if (sender.getId() == 0) {
			int lastIndex = senders.size() - 1;
			int lastId = senders.get(lastIndex).getId();
			sender.setId(lastId + 1); 
		}
		
		senders.add(sender); 
		saveAll();
	}
	
	private void saveAll() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		Resource resource = new ClassPathResource("list.json");
		File file = resource.getFile();
        objectMapper.writeValue(file, senders);
	}

	


}
