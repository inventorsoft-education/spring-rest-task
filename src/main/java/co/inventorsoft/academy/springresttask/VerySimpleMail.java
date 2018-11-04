package co.inventorsoft.academy.springresttask;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.mail.SimpleMailMessage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerySimpleMail {
	
	int id;
	@NotNull
	@Email
	String to;
	@NotNull
	String subject;
	@NotNull
	String text;
	@NotNull
	LocalDateTime sentDate;
	
	public VerySimpleMail(int id, SimpleMailMessage email) {
		this.id = id;
		this.to = (email.getTo() != null) ? email.getTo()[0] : ""; 
		this.subject = email.getSubject(); 
		this.text = email.getText();
		this.sentDate = LocalDateTime.ofInstant(email.getSentDate().toInstant(), ZoneId.systemDefault());
	}
	
	public SimpleMailMessage toSimpleMailMessage() {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(this.getTo()); 
		email.setSubject(this.getSubject()); 
		email.setText(this.getText());
		email.setSentDate( Date.from(this.getSentDate().atZone(ZoneId.systemDefault()).toInstant()) );
		return email;
	}

}
