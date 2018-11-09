package spring.rest.task.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import spring.rest.task.SimpleEmail;
import spring.rest.task.repository.EmailRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void addNewEmail(SimpleEmail simpleEmail) throws ParseException {
        SimpleMailMessage sMM = new SimpleMailMessage();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date date = dateFormat.parse(simpleEmail.getDate());
        sMM.setTo(simpleEmail.getTo());
        sMM.setSubject(simpleEmail.getSubject());
        sMM.setText(simpleEmail.getText());
        sMM.setSentDate(date);
        repository.save(sMM);
        sendScheduledEmail();

    }
    public void updateEmailDate(int id, Date date){
        List<SimpleMailMessage> simpleMailMessages = getAllEmails();
           SimpleMailMessage sMM = simpleMailMessages.get(id);
           sMM.setSentDate(date);
           simpleMailMessages.set(id, sMM);
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
