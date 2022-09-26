package com.messenger.api.controller;

import com.messenger.api.dto.EmailRequest;
import com.messenger.api.dto.EmailResponse;
import com.messenger.domain.Email;
import com.messenger.exception.NotFoundException;
import com.messenger.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EmailController {
  EmailService emailService;

  @GetMapping
  public List<EmailResponse> findAll() {
    return emailService.findAll().stream()
        .map(EmailResponse::fromEntity)
        .toList();
  }

  @GetMapping("/{id}")
  public EmailResponse find(@PathVariable Long id) {
    return emailService.findById(id)
        .map(EmailResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Email", id));
  }

  @PostMapping
  public EmailResponse create(@Valid @RequestBody EmailRequest request) {
    log.info("Received user registration request {}", request);
    return EmailResponse.fromEntity(emailService.create(request));
  }

  @PutMapping("{id}")
  public EmailResponse update(@PathVariable Long id, @RequestBody @Valid EmailRequest request) {
    Email email = emailService.findById(id)
        .orElseThrow(() -> new NotFoundException("Email", id));
    return EmailResponse.fromEntity(emailService.update(email, request));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    Email email = emailService.findById(id)
        .orElseThrow(() -> new NotFoundException("Email", id));
    emailService.delete(email);
  }
}
