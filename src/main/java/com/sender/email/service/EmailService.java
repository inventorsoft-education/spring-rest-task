package com.sender.email.service;

import com.sender.email.Email;
import com.sender.email.repos.EmailProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailService {
    private JavaMailSender mailSender;
    private ThreadPoolTaskScheduler scheduler;
    private EmailProcessing fileProcessing;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, ThreadPoolTaskScheduler scheduler, EmailProcessing fileProcessing) {
        this.mailSender = javaMailSender;
        this.scheduler = scheduler;
        this.fileProcessing = fileProcessing;
    }

    public void sendLast() {
        Email email = fileProcessing.getUnsent().get(fileProcessing.getUnsent().size()-1);
        System.out.println(email.getDeliveryDate());

        scheduler.schedule(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getRecipient());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            fileProcessing.changeStatus(email);
            mailSender.send(message);
        }, email.getDeliveryDate());
    }

    public void sendAll() {
        List<Email> emails = fileProcessing.getUnsent();

        emails.stream()
                .forEach( email -> {
                    scheduler.schedule(() -> {
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(email.getRecipient());
                        message.setSubject(email.getSubject());
                        message.setText(email.getBody());
                        fileProcessing.changeStatus(email);
                        mailSender.send(message);
                        System.out.println("DELIVERED\nRecipieint: " + email.getRecipient() + "\nSubject: " + email.getSubject() + "Date: " + email.getDeliveryDate());
                    }, email.getDeliveryDate());
                });

    }
}
