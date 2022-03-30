package com.inventor.email.service;

import com.inventor.email.Email;
import com.inventor.email.settings.EmailSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesE {
    private JavaMailSender emailSender;
    private ThreadPoolTaskScheduler scheduler;
    private EmailSettings workingFile;

    @Autowired
    public ServicesE(JavaMailSender javaMailSender, ThreadPoolTaskScheduler scheduler, EmailSettings workingFile) {
        this.emailSender = javaMailSender;
        this.scheduler = scheduler;
        this.workingFile = workingFile;
    }

    public void sendAll() {
        List<Email> emails = workingFile.getUnsent();

        emails.stream()
                .forEach( email -> {
                    scheduler.schedule(() -> {
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(email.getOderzhuvach());
                        message.setSubject(email.getTema());
                        message.setText(email.getTekst());
                        workingFile.changeCondition(email);
                        emailSender.send(message);
                        System.out.println("Доставлено\nОтримувач: " + email.getOderzhuvach() + "\nТема: " + email.getTema() + "Дата: " + email.getDataOtrymannya());
                    }, email.getDataOtrymannya());
                });

    }

    public void sendLast() {
        Email email = workingFile.getUnsent().get(workingFile.getUnsent().size()-1);
        System.out.println(email.getDataOtrymannya());

        scheduler.schedule(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getOderzhuvach());
            message.setSubject(email.getTema());
            message.setText(email.getTekst());
            workingFile.changeCondition(email);
            emailSender.send(message);
        }, email.getDataOtrymannya());
    }

}
