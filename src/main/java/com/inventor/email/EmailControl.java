package com.inventor.email;

import com.inventor.email.settings.EmailSettings;
import com.inventor.email.service.ServicesE;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EmailControl {
    EmailSettings working;
    ServicesE servicesE;

    @GetMapping("/emails")
    public List<Email> getEmails() {
        return working.getAll();
    }

    @PostMapping(consumes = "application/json")
    public void newEmail(@RequestBody List<Email> email){
        working.addEmail(email);
        servicesE.sendAll();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<String> deleteEmail(@PathVariable int id) {
        if (id >= working.getAll().size()) {
            return ResponseEntity.status(404).body("Недосяжна ціль\nПошти з вказаним id не існує");
        }
        working.deleteEmail(id);
        return ResponseEntity.ok(String.format("Пошта з номером: %s була видалена!", id));
    }

    @PutMapping(value = "/date/{id}", produces = "application/json")
    public ResponseEntity<String> updateDate(@PathVariable int id,@RequestParam(value="date") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") Date newDate){
        if (id >= working.getAll().size()) {
            return ResponseEntity.status(404).body("Недосяжна ціль\nПошти з вказаним id не існує");
        }
        working.changeDate(id, newDate);
        return ResponseEntity.ok(String.format("Час отримання пошти з id: %s був змінений!", id));
    }

}
