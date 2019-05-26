package com.example.homework6;

import com.example.homework6.controllers.EmailController;
import com.example.homework6.repos.EmailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

@Component
public class EmailDeliver extends Thread{

    @Autowired
    EmailController emailController;

    @Autowired
    EmailRepo emailRepo;

    @Override
    public void run() {
        try{
            for(EmailItem emailItem: emailController.getActive()){
                if(getSimpleMessage(emailItem).getSentDate().compareTo(new Date()) <= 0){
                    sendEmail(getSimpleMessage(emailItem));
                    emailItem.setStatusDelivery(true);
                    emailRepo.save(emailItem);
                }
            }
        }catch (Exception e){
            this.yield();
            e.printStackTrace();
        }


    }

    private void sendEmail(SimpleMailMessage email) {

        try{
            this.getJavaMailSender().send(email);
        }
        catch (MailException ex){
            ex.printStackTrace();
        }
    }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("yuliya.plishkina@gmail.com");
        mailSender.setPassword("Freedom555");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public static SimpleMailMessage getSimpleMessage(EmailItem emailItem){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(emailItem.getEmailSubject());
        msg.setTo(emailItem.getEmailRecipient());
        msg.setText(emailItem.getEmailBody());
        msg.setSentDate(DateValidator.getDate(emailItem.getDeliveryDate()));
        return msg;
    }
}