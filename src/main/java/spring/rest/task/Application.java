package spring.rest.task;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import spring.rest.task.services.EmailService;

import java.io.IOException;
import java.util.Date;


@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
       ApplicationContext context = SpringApplication.run(Application.class, args);
       EmailService emailService = context.getBean(EmailService.class);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("symyniuk.mykhailo@gmail.com");
        simpleMailMessage.setSubject("subject");
        simpleMailMessage.setText("text");
        simpleMailMessage.setSentDate(new Date());
       emailService.addNewEmail(simpleMailMessage);

        System.out.println(emailService.getAllEmails());
//       emailService.sendScheduledEmail();


    }
}
