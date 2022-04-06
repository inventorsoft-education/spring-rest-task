package com.inventor.email;

import com.inventor.email.settings.EmailSettings;
import com.inventor.email.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/emails")
@AllArgsConstructor
public class EmailControl {
    EmailSettings working;
    EmailService emailService;

    @GetMapping("/emails")
    public List<Email> getEmails() {
        return working.getAll();
    }

    @PostMapping("/api/emails")
    public void newEmail(@RequestBody List<Email> email) {
        working.addEmail(email);
        emailService.sendAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmail(@PathVariable int id) {
        if (id >= working.getAll().size()) {
            return ResponseEntity.status(404).body("Unreached statement\nEmail with entered id not exist");
        }
        working.deleteEmail(id);
        return ResponseEntity.ok(String.format("Email with id: %s has been deleted!", id));
    }

    @PutMapping("/api/emails/{id}")
    public ResponseEntity<String> updateDate(@PathVariable int id, LocalDateTime newDate) {
        if (id >= working.getAll().size()) {
            return ResponseEntity.status(404).body("Unreached statement\nEmail with entered id not exist");
        }
        working.changeDate(id, newDate);
        return ResponseEntity.ok(String.format("Time of email receiving: %s has been changed!", id));
    }

}
