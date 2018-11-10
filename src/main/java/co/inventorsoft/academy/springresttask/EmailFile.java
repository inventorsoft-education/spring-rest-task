package co.inventorsoft.academy.springresttask;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class EmailFile implements EmailDAO {
	
	private String file;
	private List<SimpleMailMessage> emails;
	
	@SuppressWarnings("unchecked")
	public EmailFile(@Value("${email.file}") String file) {
		this.file = file;
		emails = new CopyOnWriteArrayList<SimpleMailMessage>();
		if(Files.exists(Paths.get(file))) {
			try(FileInputStream fis = new FileInputStream(file); 
				ObjectInputStream ois = new ObjectInputStream(fis)) {
						emails = (CopyOnWriteArrayList<SimpleMailMessage>) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<SimpleMailMessage> getAll() {
		return emails;
	}
	
	@Override
	public SimpleMailMessage get(int id) {
		return emails.get(id);
	}
	
	@Override
	public void add(SimpleMailMessage email) {
		emails.add(email);
	}
	
	@Override
	public void add(List<SimpleMailMessage> emails) {
		this.emails.addAll(emails);
	}

	@Override
	public void delete(SimpleMailMessage email) {
		emails.remove(email);
	}

	@Override
	public void delete(int id) {
		emails.remove(id);
	}
	
	@Override
	public void clear() {
		emails.clear();
	}
	
	@Override
	public void update(int id, LocalDateTime date) {
    	emails.get(id).setSentDate( Date.from(date.atZone(ZoneId.systemDefault()).toInstant()) );
	}
	
	@PreDestroy
	@Scheduled(cron = "0 0/15 * * * ?")
	private void save() {
		try(FileOutputStream fos = new FileOutputStream(file); 
			ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					oos.writeObject(emails);
		} catch (IOException e) {
			e.printStackTrace();
			deleteEmailFile();
		}
	}

	private void deleteEmailFile() {
		try {
			Files.deleteIfExists(Paths.get(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
