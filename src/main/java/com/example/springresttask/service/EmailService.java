package com.example.springresttask.service;

import com.example.springresttask.domain.Email;
import com.example.springresttask.repository.EmailRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    public List<Email> pendingEmailDeliveries() {
        return emailRepository.findAllByPendingEmail();
    }

    @Transactional
    public Email createEmailDelivery(Email email) {
        return emailRepository.save(email);
    }

    @Transactional
    public Email updateDeliveryDate(Email email) {
        if (!emailRepository.existsById(email.getId())) {
            throw new RuntimeException("id not found");
        }

        if (email.getIsSent()) {
            throw new RuntimeException("you cannot change the date because the letter" +
                    " has already been sen");
        }
        return emailRepository.save(email);
    }

    public void removePendingEmail(Long id) {
        emailRepository.deletePendingEmail(id);
    }


}
