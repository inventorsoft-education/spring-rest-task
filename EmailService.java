package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;


@Service
@EnableScheduling
public class EmailService {

    SimpleMailMessage simpleMailMessage;
    JavaMailSender javaMailSender;
    ArrayList<Email> allEmailsList;
    WorkWithJsonFile jsonFileWork;

    @Autowired
    public EmailService(WorkWithJsonFile jsonFile,JavaMailSender javaMailSender) {
        this.jsonFileWork=jsonFile;
        this.javaMailSender=javaMailSender;
        simpleMailMessage=new SimpleMailMessage();
        allEmailsList=jsonFileWork.getEmails();
    }


    @Scheduled(fixedRate = 20000)
    public void lookingForCurrentEmails() {

        for (Email counter:allEmailsList){
            if (counter.getDeliveryDate().equals(new Date()) && counter.getSended().equals(false)){
                System.out.println(1);
                simpleMailMessage.setTo(counter.getRecepientName());
                simpleMailMessage.setSubject(counter.getEmailSubject());
                simpleMailMessage.setText(counter.getEmailBody());
                javaMailSender.send(simpleMailMessage);
                counter.setSended(true);
                jsonFileWork.updateList(allEmailsList);
            }
        }

    }


}
