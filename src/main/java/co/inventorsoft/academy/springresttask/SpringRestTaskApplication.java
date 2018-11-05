package co.inventorsoft.academy.springresttask;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class SpringRestTaskApplication implements CommandLineRunner {

	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailDAO emailDAO;
	@Autowired
	private EmailConsole emailConsole;

	public static void main(String[] args) {
		SpringApplication.run(SpringRestTaskApplication.class, args);
	}

	@Bean
	public static Logger getLogger() {
		return LoggerFactory.getLogger(SpringRestTaskApplication.class); 
	}

	@Bean
	@Scope("prototype")
	public SimpleMailMessage deafultEmail(
								@Value("${test.mail.addr}") String addr,
								@Value("${test.mail.subj}") String subj,
								@Value("${test.mail.text}") String text,
								@Value("${test.mail.delaySec}") Long delay) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(addr); 
		email.setSubject(subj); 
		email.setText(text);
		email.setSentDate( Date.from(LocalDateTime.now().plusSeconds(delay).atZone(ZoneId.systemDefault()).toInstant()) );
		return email;
	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(2);
		threadPoolTaskScheduler.setThreadNamePrefix("EmailScheduler");
		threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
        return threadPoolTaskScheduler;
	}
	
	@Override
	public void run(String... args) {
		emailDAO.add( emailConsole.getEmails() );
		emailDAO.save();
		emailService.sendFutureEmail();
	}
	
}
