package com.example.springresttask.service;

import com.example.springresttask.domain.Email;
import com.example.springresttask.repository.EmailRepository;
import java.time.LocalDateTime;
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
    public Integer updateDeliveryDate(Long id, LocalDateTime deliveryDate){
        return emailRepository.updateEmailBody(id,  deliveryDate);
    }

    public void removePendingEmail(Long id) {
          emailRepository.deletePendingEmail(id);
    }
}
