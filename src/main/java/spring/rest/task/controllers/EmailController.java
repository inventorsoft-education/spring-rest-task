package spring.rest.task.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import spring.rest.task.SimpleEmail;
import spring.rest.task.services.EmailService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/emails")
public class EmailController {
    EmailService emailService;
    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @GetMapping
    public List<SimpleMailMessage> getAllEmails(){
        return emailService.getAllEmails();
    }
    @PostMapping(consumes = "application/json")
    public void addNewEmail(@RequestBody SimpleEmail simpleEmail) throws ParseException {
        emailService.addNewEmail(simpleEmail);
    }
    @PutMapping(value = "/{id}", consumes = "application/json")
    public void updateDeliveryDate(@PathVariable int id, @RequestParam String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date date1 = dateFormat.parse(date);
        emailService.updateEmailDate(id, date1);
    }
    @DeleteMapping
    public void removePendingEmailDelivery(){
        emailService.removePendingEmails();
    }
}
