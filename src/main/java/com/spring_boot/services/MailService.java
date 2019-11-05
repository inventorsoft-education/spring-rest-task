package com.spring_boot.services;

import com.spring_boot.entity.Letter;
import com.spring_boot.repository.MailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${file.fileNameToSend}")
    private String mailsToSend;

    @Value("${file.fileNameOfSent}")
    private String sentMails;


    @Autowired
    public MailService(JavaMailSender javaMailSender, MailDao mailDao) {
        this.mailDao = mailDao;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "* */1 * * * *")
    public void sendMail() {
        LinkedList<Letter> letters = mailDao.getMails(mailsToSend);
        for (Letter letterToSend : letters){
            if (letterToSend.getDeliveryTime().compareTo(LocalDateTime.now())<1){
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(letterToSend.getRecipient());
                msg.setSubject(letterToSend.getSubject());
                msg.setText(letterToSend.getBody());
                msg.setSentDate(Timestamp.valueOf(letterToSend.getDeliveryTime()));
                javaMailSender.send(msg);
                mailDao.removeSentMail(letterToSend);
            }
        }

    }

    public void saveMail(Letter letter) {
        mailDao.saveMail(letter, mailsToSend);
    }

    public List<Letter> getMailsToSend() {
        return mailDao.getMails(mailsToSend);
    }

    public List<Letter> getSentMails() {
        return mailDao.getMails(sentMails);
    }

}
