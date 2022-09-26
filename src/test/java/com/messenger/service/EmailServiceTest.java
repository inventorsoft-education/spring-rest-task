package com.messenger.service;

import com.messenger.api.dto.EmailRequestFixture;
import com.messenger.api.dto.EmailRequest;
import com.messenger.domain.Email;
import com.messenger.repository.EmailRepository;
import com.messenger.service.impl.EmailServiceImpl;
import com.messenger.domain.EmailFixture;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class EmailServiceTest {
  @Mock
  EmailRepository emailRepository;
  @InjectMocks
  EmailServiceImpl emailService;
  @Captor
  ArgumentCaptor<Email> emailArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;


  @Test
  void findAll() {
    List<Email> emails = List.of(EmailFixture.createEmail());
    when(emailRepository.findAll()).thenReturn(emails);
    List<Email> actualEmail = emailService.findAll();
    assertThat(emails).isEqualTo(actualEmail);
  }

  @Test
  void create() {
    EmailRequest request = EmailRequestFixture.createEmailRequest();
    emailService.create(request);
    verify(emailRepository).save(emailArgumentCaptor.capture());
    Email actualEmail = emailArgumentCaptor.getValue();
    assertThatEmailMappedCorrectly(request, actualEmail);
  }

  @Test
  void findById() {
    Email email = EmailFixture.createEmail();
    when(emailRepository.findById(anyLong())).thenReturn(Optional.ofNullable(email));
    emailService.findById(email.getId());
    verify(emailRepository).findById(longArgumentCaptor.capture());
    Long id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(email.getId());
  }

  @Test
  void update() {
    Email email = EmailFixture.createEmail();
    EmailRequest request = EmailRequestFixture.createEmailRequest();
    emailService.update(email, request);
    verify(emailRepository).save(emailArgumentCaptor.capture());
    Email actualEmail = emailArgumentCaptor.getValue();
    assertThatEmailMappedCorrectly(request, actualEmail);

  }

  @Test
  void delete() {
    Email email = EmailFixture.createEmail();
    emailService.delete(email);
    verify(emailRepository).delete(emailArgumentCaptor.capture());
    Email actualEmail = emailArgumentCaptor.getValue();
    assertThat(actualEmail).isEqualTo(email);
  }

  private void assertThatEmailMappedCorrectly(EmailRequest request, Email actualEmail) {
    assertThat(actualEmail.getFrom()).isEqualTo(request.getFrom());
    assertThat(actualEmail.getTo()).isEqualTo(request.getTo());
    assertThat(actualEmail.getCc()).isEqualTo(request.getCc());
    assertThat(actualEmail.getSubject()).isEqualTo(request.getSubject());
    assertThat(actualEmail.getBody()).isEqualTo(request.getBody());
    assertThat(actualEmail.getDeliveryDate()).isEqualTo(request.getDeliveryDate());
  }
}
