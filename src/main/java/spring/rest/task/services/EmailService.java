package spring.rest.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import spring.rest.task.EmailConsoleReader;
import spring.rest.task.repository.EmailRepository;

import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class EmailService {
    @Value("${email.filename}")
    private String filename;
    private EmailConsoleReader emailConsoleReader;
    private JavaMailSender javaMailSender;
    private TaskScheduler taskScheduler;
    private EmailRepository repository;
    @Autowired
    public EmailService(EmailConsoleReader emailConsoleReader,
                        JavaMailSender javaMailSender,
                        TaskScheduler taskScheduler,
                        EmailRepository repository) {
        this.emailConsoleReader = emailConsoleReader;
        this.javaMailSender = javaMailSender;
        this.taskScheduler = taskScheduler;
        this.repository = repository;
    }

    public List<SimpleMailMessage> getAllEmails()  {
        return repository.findAll();
    }
    public void addNewEmail(SimpleMailMessage simpleMailMessage) throws IOException {
        repository.save(simpleMailMessage);
        sendScheduledEmail();
    }
    public void addNewEmailFromConsole(){
        emailConsoleReader.readEmail();
    }
    public void updateEmailDate(int id, Date date){
        List<SimpleMailMessage> simpleMailMessages;
           simpleMailMessages = getAllEmails();
           SimpleMailMessage sMM = simpleMailMessages.get(id);
           sMM.setSentDate(date);
           simpleMailMessages.add(id, sMM);
           repository.save(simpleMailMessages);
    }
    public void removePendingEmails(){
        repository.deleteAll();
    }


    public void sendScheduledEmail() throws IOException {
        List<SimpleMailMessage>simpleMailMessages = getAllEmails();
        for(SimpleMailMessage email : simpleMailMessages){
            taskScheduler.schedule(()->{
                javaMailSender.send(email);
                clearEmail(email);
            },email.getSentDate());
        }
    }
    public void clearEmail(SimpleMailMessage simpleMailMessage){
        repository.delete(simpleMailMessage);
    }
}
