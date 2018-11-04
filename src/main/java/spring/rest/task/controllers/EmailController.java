package spring.rest.task.controllers;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import spring.rest.task.services.EmailService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController(value = "/emails")
public class EmailController {
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public List<SimpleMailMessage> getAllEmails(){
       return emailService.getAllEmails();
    }
    @PostMapping(value = "emails/new")
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
    @PutMapping(value = "emails/update")
    public void updateDeliveryDate(int id, String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date date1 = dateFormat.parse(date);
        emailService.updateEmailDate(id, date1);
    }
    @DeleteMapping
    public void removePendingEmailDelivery(){
        emailService.removePendingEmails();
    }
}
