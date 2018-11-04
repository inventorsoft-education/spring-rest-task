package co.inventorsoft.academy.springresttask.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PostMapping(value = "/new", consumes = "application/json")
    public void newEmail(@Valid @RequestBody VerySimpleMail mail) {
    	emailDAO.add( mail.toSimpleMailMessage() );
    	emailDAO.save();
		emailService.resendFutureEmail();
    }

    @PatchMapping(value = "/update", produces = "application/json")
    public ResponseEntity<String> updateDate(@RequestParam int id, 
    								@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
    	if( id>=0 && id<emailDAO.getAll().size() ) {
        	emailDAO.get(id).setSentDate( Date.from(date.atZone(ZoneId.systemDefault()).toInstant()) );;
        	emailDAO.save();
    		emailService.resendFutureEmail();
        	return ResponseEntity.ok("{\"message\" : \"Send Date was changed\"}");
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\" : \"Index out of range\"}");
    	}
    }

    @DeleteMapping(value = "/remove", produces = "application/json")
    public ResponseEntity<String> removeEmail(@RequestParam int id) {
    	if( id>=0 && id<emailDAO.getAll().size() ) {
        	emailDAO.delete(id);
        	emailDAO.save();
    		emailService.resendFutureEmail();
        	return ResponseEntity.ok("{\"message\" : \"Email was removed\"}");
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\" : \"Index out of range\"}");
    	}
    }

}
