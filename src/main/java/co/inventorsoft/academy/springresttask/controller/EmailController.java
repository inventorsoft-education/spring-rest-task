package co.inventorsoft.academy.springresttask.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.inventorsoft.academy.springresttask.EmailDAO;
import co.inventorsoft.academy.springresttask.EmailService;
import co.inventorsoft.academy.springresttask.EmailsToWeb;
import co.inventorsoft.academy.springresttask.VerySimpleMail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(value = "/emails")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EmailController {
	
	EmailDAO emailDAO;
	EmailsToWeb emailsToWeb;
	EmailService emailService;

    @GetMapping(produces = "application/json")
    public List<VerySimpleMail> getEmails() {
        return emailsToWeb.convert( emailDAO.getAll() );
    }

    @PostMapping(consumes = "application/json")
    public void newEmail(@Valid @RequestBody VerySimpleMail mail) {
    	SimpleMailMessage email = mail.toSimpleMailMessage();
    	emailDAO.add(email);
		emailService.sendNewEmail(email);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> updateDate(@PathVariable int id, 
    								@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
    	try {
        	SimpleMailMessage email = emailDAO.get(id);
        	emailDAO.update(id, date);
    		emailService.sendEmailNewDate(email);
        	return ResponseEntity.ok("{\"message\" : \"Send Date was changed\"}");
    	} catch(IndexOutOfBoundsException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\" : \"Index out of range\"}");
    	}
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> removeEmail(@PathVariable int id) {
    	try {
        	SimpleMailMessage email = emailDAO.get(id);
        	emailDAO.delete(id);
    		emailService.cancelEmail(email);
        	return ResponseEntity.ok("{\"message\" : \"Email was removed\"}");
    	} catch(IndexOutOfBoundsException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\" : \"Index out of range\"}");
    	}
    }

}
