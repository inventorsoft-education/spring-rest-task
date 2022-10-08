package main;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailsDataBase {
    HashMap<Long, Email> emailsData = new HashMap<>();
    Long nextId = 0L;
    @Autowired
    ReadFile backup;

    public EmailsDataBase(ReadFile backup) {
        this.backup = backup;
        restoreEmailsFromBackup();
    }

    private void restoreEmailsFromBackup() {
        LinkedList<Email> emails = backup.readBackup();
        long maxId = 0;
        for (Email email : emails) {
            maxId = Math.max(email.getId() + 1, maxId);
            emailsData.put(email.getId(), email);
        }
        nextId = maxId;
    }

    private Long getNextId() {
        return nextId++;
    }

    public List<Email> getAllEmails() {
         return new ArrayList<>(emailsData.values());
    }

    public Email save(Email email) {
        save(getNextId(), email);
        return email;
    }

    public Email save(Long id, Email email) {
        emailsData.put(id, email);
        return email;
    }

    public Email findById(Long id) {
        Email email = emailsData.get(id);
        if(email == null) {
            throwEntityNotFoundException(id);
        }
        return email;
    }

    public void delete(Long id) {
        if(emailsData.containsKey(id)) {
            emailsData.remove(id);
        } else {
            throwEntityNotFoundException(id);
        }
    }

    public Email update(Long id, Email email) {
        if(emailsData.containsKey(id)) {
            emailsData.put(id, email);
        } else {
            throwEntityNotFoundException(id);
        }
        return email;
    }

   private void throwEntityNotFoundException(Long id) {
       throw new EntityNotFoundException("There is no email with ID: " + id + " in Database!");
   }
}
