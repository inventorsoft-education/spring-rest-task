package co.inventorsoft.academy.springresttask;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

/**
 * work with threads was improved
 * 
 * Field "futures" is initialized from class ConcurrentSkipListMap because ConcurrentHashMap is inappropriate 
 * SimpleMailMessage is a key and SimpleMailMessage.hashCode() depends on its fields
 */
@Service
public class EmailService {

	private JavaMailSender emailSender;
	private Logger log;
	private EmailDAO emailDAO;
	private ThreadPoolTaskScheduler scheduler;
	
	private Map<SimpleMailMessage, ScheduledFuture<?>> futures = 
			new ConcurrentSkipListMap<>( (x,y) -> System.identityHashCode(x)-System.identityHashCode(y) );
	
	@Autowired
	public EmailService(JavaMailSender emailSender, Logger log, EmailDAO emailDAO, ThreadPoolTaskScheduler scheduler) {
		this.emailSender = emailSender;
		this.log = log;
		this.emailDAO = emailDAO;
		this.scheduler = scheduler;
	}

	public void sendFutureEmail() {
		for(SimpleMailMessage email : emailDAO.getAll()) {
			log.info(" E-mail  To:{}  Subject:{}  Date:{} ",email.getTo(),email.getSubject(),email.getSentDate());
			futures.put(email, sendOneEmail(email));
		}
		loggingState(); 
	}

	public void sendNewEmail(SimpleMailMessage email) {
		log.info(" New E-mail  To:{}  Subject:{}  Date:{} ",email.getTo(),email.getSubject(),email.getSentDate());
		futures.put(email, sendOneEmail(email));
		loggingState(); 
	}

	public void sendEmailNewDate(SimpleMailMessage email) {
		log.warn(" New Date for E-mail  To:{}  Subject:{}  Date:{} ",email.getTo(),email.getSubject(),email.getSentDate());
		futures.get(email).cancel(true);
		futures.put(email, sendOneEmail(email));
		loggingState(); 
	}

	public void cancelEmail(SimpleMailMessage email) {
		log.warn(" Delete E-mail  To:{}  Subject:{}  Date:{} ",email.getTo(),email.getSubject(),email.getSentDate());
		futures.get(email).cancel(true);
		futures.remove(email);
		loggingState(); 
	}
	
	private ScheduledFuture<?> sendOneEmail(SimpleMailMessage email) {
		return scheduler.schedule( () -> 
				{
					try{
						log.info("\n Sending E-mail \n To: {} \n Subject: {} \n Date: {} ",email.getTo(),email.getSubject(),email.getSentDate());
						emailSender.send(email);
						futures.remove(email);
						emailDAO.delete(email);
					} catch(Exception e) {
						log.warn(" E-mail  To:{}  Subject:{}  Date:{} ",email.getTo(),email.getSubject(),email.getSentDate());
						log.error(e.getMessage());
					}
				},
				email.getSentDate() );
	}

	private void loggingSchedulerState() {
		log.info(" State  {}:{} ",scheduler.getThreadNamePrefix(),scheduler.getScheduledThreadPoolExecutor()); 
	}

	private void loggingFuturesState() {
		log.info(" State  ScheduledFuture:{} ",futures.size()); 
	}

	private void loggingState() {
		loggingSchedulerState(); 
		loggingFuturesState();
	}

}
