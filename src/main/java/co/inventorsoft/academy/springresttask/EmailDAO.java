package co.inventorsoft.academy.springresttask;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;

public interface EmailDAO {
	
	List<SimpleMailMessage> getAll();
	SimpleMailMessage get(int id);
	void add(SimpleMailMessage email);
	void add(List<SimpleMailMessage> emails);
	void delete(SimpleMailMessage email);
	void delete(int id);
	void clear();
	void update(int id, LocalDateTime date);

}
