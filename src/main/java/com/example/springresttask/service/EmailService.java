package com.example.springresttask.service;

import com.example.springresttask.domain.Email;
import com.example.springresttask.domain.dto.EmailDto;
import com.example.springresttask.domain.mapper.EmailMapper;
import com.example.springresttask.repository.EmailRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;

    public List<Email> pendingEmailDeliveries() {
        return emailRepository.findAllByPendingEmail();
    }

    @Transactional
    public Email createEmailDelivery(Email email) {
        return emailRepository.save(email);
    }

    @Transactional
    public EmailDto updateDeliveryDate(Long id, EmailDto emailDto) {
        Email email1 = emailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Email not found!"));

        if (emailDto.getIsSent()) {
            throw new RuntimeException("you cannot change the date because the letter" +
                    " has already been sen");
        }
        emailMapper.update(emailDto,email1);
        return emailMapper.toDto(emailRepository.save(email1));
    }

    public void removePendingEmail(Long id) {
        emailRepository.deletePendingEmail(id);
    }


}
