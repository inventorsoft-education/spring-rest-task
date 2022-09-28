package com.messenger.service.impl;

import com.messenger.api.dto.request.EmailRequest;
import com.messenger.domain.Email;
import com.messenger.repository.EmailRepository;
import com.messenger.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {

  EmailRepository emailRepository;

  @Override
  public List<Email> findAll() {
    return emailRepository.findAll();
  }

  @Override
  public Optional<Email> findById(Long id) {
    return emailRepository.findById(id);
  }

  @Override
  public Email create(EmailRequest request, String userEmail) {
    return save(Email.create(request, userEmail));
  }

  private Email save(Email email) {
    return emailRepository.save(email);
  }

  @Override
  public Email update(Email email, EmailRequest request) {
    email.update(request);
    return save(email);
  }

  @Override
  public void delete(Email email) {
    emailRepository.delete(email);
  }
}
