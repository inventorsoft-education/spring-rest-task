package co.inventorsoft.springresttask.service.repository;

import co.inventorsoft.springresttask.service.model.Email;

import java.util.List;

public interface EmailRepository {

    Email getEmail(Integer id);

    List<Email> listEmails();

    Email createEmail(Email email);

    Email updateEmail(Integer id, Email email);

    void deleteEmail(Integer id);

}
