package spring.rest.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.rest.task.services.EmailService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class EmailController {
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(value = "/emails", method = RequestMethod.GET)
    public List<SimpleMailMessage> getAllEmails(){
       return emailService.getAllEmails();
    }
    @RequestMapping(value = "/emails/new", method = RequestMethod.POST)
    public void addNewEmail(String to, String subject, String text, String date) throws ParseException, IOException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date date1 = dateFormat.parse(date);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setSentDate(date1);
        emailService.addNewEmail(simpleMailMessage);
    }
    @RequestMapping(value = "/emails/update", method = RequestMethod.PUT)
    public void updateDeliveryDate(int id, String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date date1 = dateFormat.parse(date);
        emailService.updateEmailDate(id, date1);
    }
}
