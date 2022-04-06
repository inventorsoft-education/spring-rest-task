package com.inventor.email.service;

import com.inventor.email.Email;
import com.inventor.email.settings.EmailSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
public class EmailService extends ControllerExceptionHandler {
    private JavaMailSender emailSender;
    private ThreadPoolTaskScheduler scheduler;
    private EmailSettings workingFile;


    @Autowired
    public EmailService(JavaMailSender javaMailSender, ThreadPoolTaskScheduler scheduler, EmailSettings workingFile) {
        this.emailSender = javaMailSender;
        this.scheduler = scheduler;
        this.workingFile = workingFile;
    }

    public void sendAll() {
        List<Email> emails = workingFile.getUnsent();

        emails.stream()
                .forEach(email -> {
                    scheduler.schedule(() -> {
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(email.getReceiver());
                        message.setSubject(email.getSubject());
                        message.setText(email.getText());
                        workingFile.changeCondition(email);
                        emailSender.send(message);
                        System.out.println("Received\nReceiver: " + email.getReceiver() + "\nSubject: " + email.getSubject() + "Date: " + email.getDateOfReceiving());
                    }, Instant.from(email.getDateOfReceiving()));
                });

    }
}
