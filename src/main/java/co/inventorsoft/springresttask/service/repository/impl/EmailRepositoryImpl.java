package co.inventorsoft.springresttask.service.repository.impl;

import co.inventorsoft.springresttask.service.model.Email;
import co.inventorsoft.springresttask.service.repository.EmailRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private final List<Email> emailList = new ArrayList<>();

    @Override
    public Email getEmail(int id) {
        return emailList.stream()
                .filter(email -> email.getId() == (id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Email is not found!"));
    }

    @Override
    public List<Email> listEmails() {
        return new ArrayList<>(emailList);
    }

    @Override
    public Email createEmail(Email email) {
        emailList.add(email);
        return email;
    }

    @Override
    public Email updateEmail(int id, Email email) {
        boolean isDeleted = emailList.removeIf(u -> u.getId() == (id));
        if (isDeleted) {
            emailList.add(email);
        } else {
            throw new RuntimeException("Email is not found!");
        }
        return email;
    }

    @Override
    public void deleteEmail(int id) {
        emailList.removeIf(email -> email.getId() == (id));
    }

}
