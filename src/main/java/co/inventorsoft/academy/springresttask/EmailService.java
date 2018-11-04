package co.inventorsoft.academy.springresttask;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private JavaMailSender emailSender;
	private Logger log;
	private EmailDAO emailDAO;
	private ThreadPoolTaskScheduler scheduler;
	
	@Autowired
	public EmailService(JavaMailSender emailSender, Logger log, EmailDAO emailDAO, ThreadPoolTaskScheduler scheduler) {
		this.emailSender = emailSender;
		this.log = log;
		this.emailDAO = emailDAO;
		this.scheduler = scheduler;
	}

	public void sendFutureEmail() {
		List<SimpleMailMessage> emails = emailDAO.getAll();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("EmailScheduler");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
		for(SimpleMailMessage email : emails) {
			log.info(" E-mail  To:{}  Subject:{}  Date:{} ",email.getTo(),email.getSubject(),email.getSentDate());
			scheduler.schedule( () -> 
				{
					try{
						log.info("\n Sending E-mail \n To: {} \n Subject: {} \n Date: {} ",email.getTo(),email.getSubject(),email.getSentDate());
						emailSender.send(email);
						emailDAO.delete(email);
						emailDAO.save();
					} catch(Exception e) {
						log.warn(" E-mail  To:{}  Subject:{}  Date:{} ",email.getTo(),email.getSubject(),email.getSentDate());
						log.error(e.getMessage());
					}
				},
				email.getSentDate() );
		}
		scheduler.shutdown();
	}

	@Lookup
    public ThreadPoolTaskScheduler getThreadPoolTaskScheduler(){
        return null;
    }

    /**
     * Restart ScheduledThreadPoolExecutor is the simplest workaround, but not the best solution  
     * Tuning the threads is beyond the scope of this home task
     */
	public void resendFutureEmail() {
		log.warn(" Restart  {}:{} ",scheduler.getThreadNamePrefix(),scheduler.getScheduledThreadPoolExecutor());
        scheduler.setWaitForTasksToCompleteOnShutdown(false);
        scheduler.shutdown();
        scheduler = getThreadPoolTaskScheduler();
		sendFutureEmail();
	}

}
