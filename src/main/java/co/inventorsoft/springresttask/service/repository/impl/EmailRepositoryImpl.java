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
    public Email getEmail(Integer id) {
        return emailList.stream()
                .filter(email -> email.getId().equals(id))
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
    public Email updateEmail(Integer id, Email email) {
        Email savedEmail = emailList.stream()
                .filter(currentEmail -> currentEmail.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Email is not found!"));
        savedEmail.setDate(email.getDate());
        return savedEmail;
    }

    @Override
    public void deleteEmail(Integer id) {
        emailList.removeIf(email -> email.getId().equals(id));
    }

}
