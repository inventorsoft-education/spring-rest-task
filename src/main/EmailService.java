package main;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static lombok.AccessLevel.PRIVATE;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmailService {
    @Autowired
    EmailsDataBase emailsDataBase;

    public EmailService(EmailsDataBase emailsDataBase) {
        this.emailsDataBase = emailsDataBase;
    }

    public Email saveEmail(Email email) {
        return emailsDataBase.save(email);
    }

    public List<Email> getEmails() {
        return emailsDataBase.getAllEmails();
    }

    public Email getEmailById(Long id) {
        return emailsDataBase.findById(id);
    }

    public void deleteEmail(Long id) {
        emailsDataBase.delete(id);

    }
    public Email updateEmail(Long id, Email message) {
        return emailsDataBase.update(id, message);
    }
}
