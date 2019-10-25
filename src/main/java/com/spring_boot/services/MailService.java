package com.spring_boot.services;

import com.home_work.spring_boot.entity.Letter;
import com.home_work.spring_boot.repository.MailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class MailService {

    private JavaMailSender javaMailSender;
    private MailDao mailDao;
    private static final String MAILS_TO_SEND = "fileNameToSend";
    private static final String SENT_MAILS = "fileNameOfSent";


    @Autowired
    public MailService(JavaMailSender javaMailSender, MailDao mailDao) {
        this.mailDao = mailDao;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void sendMail() {
        LinkedList<Letter> letters = mailDao.getMails(MAILS_TO_SEND);
        for (Letter lettertoSend : letters){
            if (lettertoSend.getDeliveryTime().compareTo(LocalDateTime.now())<1){
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(lettertoSend.getRecipient());
                msg.setSubject(lettertoSend.getSubject());
                msg.setText(lettertoSend.getBody());
                msg.setSentDate(Timestamp.valueOf(lettertoSend.getDeliveryTime()));
                javaMailSender.send(msg);
                mailDao.removeSentMail(lettertoSend);
            }
        }

    }

    public void saveMail(Letter letter) {
        mailDao.saveMail(letter, MAILS_TO_SEND);
    }

    public List<Letter> getMailsToSend() {
        return mailDao.getMails(MAILS_TO_SEND);
    }

    public List<Letter> getSentMails() {
        return mailDao.getMails(SENT_MAILS);
    }

}
