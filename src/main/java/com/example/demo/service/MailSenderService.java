package com.example.demo.service;

import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private JavaMailSender mailSender;

    @Autowired
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(message.getEmailTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getMessage());

        mailSender.send(mailMessage);
    }
}
