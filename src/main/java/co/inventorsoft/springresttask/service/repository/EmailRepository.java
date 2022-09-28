package co.inventorsoft.springresttask.service.repository;

import co.inventorsoft.springresttask.service.model.Email;

import java.util.List;

public interface EmailRepository {

    Email getEmail(int id);

    List<Email> listEmails();

    Email createEmail(Email email);

    Email updateEmail(int id, Email email);

    void deleteEmail(int id);

}
