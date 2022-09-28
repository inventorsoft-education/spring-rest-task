package com.messenger.service;

import com.messenger.api.dto.request.EmailRequest;
import com.messenger.domain.Email;

import java.util.List;
import java.util.Optional;

public interface EmailService {

  List<Email> findAll();

  Optional<Email> findById(Long id);

  Email create(EmailRequest request, String userEmail);

  Email update(Email email, EmailRequest request);

  void delete(Email email);

}
