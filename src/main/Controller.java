package main;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Controller {
    @Autowired
     EmailService emailService;

    public Controller(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Email addEmail(@Valid @RequestBody Email email) {
        return emailService.saveEmail(email);
    }

    @GetMapping
    public List<Email> showAllEmails() {
        List<Email> allEmails = emailService.getEmails();
        return allEmails;
    }

    @GetMapping("/{id}")
    public Email getEmailById(@PathVariable Long id) {
        return emailService.getEmailById(id);
    }
    @PutMapping("/{id}")
    public Email updateEmail( @PathVariable Long id ,@Valid @RequestBody Email email) {
        return emailService.updateEmail(id, email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmail(@PathVariable Long id) {
         emailService.deleteEmail(id);
    }
}
