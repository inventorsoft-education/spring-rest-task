package spring.rest.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import spring.rest.task.EmailConsoleReader;
import spring.rest.task.repository.EmailRepository;
import java.util.Date;
import java.util.List;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    private TaskScheduler taskScheduler;
    private EmailRepository repository;
    @Autowired
    public EmailService(JavaMailSender javaMailSender,
                        TaskScheduler taskScheduler,
                        EmailRepository repository) {
        this.javaMailSender = javaMailSender;
        this.taskScheduler = taskScheduler;
        this.repository = repository;
    }
    public List<SimpleMailMessage> getAllEmails()  {
        return repository.findAll();
    }
    public void addNewEmail(SimpleMailMessage simpleMailMessage){
        repository.save(simpleMailMessage);
        sendScheduledEmail();
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
    public void sendScheduledEmail(){
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
