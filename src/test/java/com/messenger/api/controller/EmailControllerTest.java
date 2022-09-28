package com.messenger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.api.dto.request.EmailRequest;
import com.messenger.api.dto.EmailRequestFixture;
import com.messenger.api.dto.response.EmailResponse;
import com.messenger.api.dto.response.ErrorResponse;
import com.messenger.domain.Email;
import com.messenger.domain.User;
import com.messenger.domain.UserFixture;
import com.messenger.service.EmailService;
import com.messenger.domain.EmailFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmailController.class)
@AutoConfigureMockMvc
class EmailControllerTest extends AbstractControllerTest{
  public static final String URL = "/api/v1/emails";
  @Autowired
  MockMvc mockMvc;
  @MockBean
  EmailService emailService;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void findAll_ReturnsEmails() throws Exception {
    Email email = EmailFixture.createEmail();
    EmailResponse response = EmailResponse.fromEntity(email);
    List<EmailResponse> responses = List.of(response);
    when(emailService.findAll()).thenReturn(List.of(email));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void findById_ValidEmailId_ReturnsEmail() throws Exception {
    Email email = EmailFixture.createEmail();
    EmailResponse response = EmailResponse.fromEntity(email);
    long id = 1L;
    when(emailService.findById(id)).thenReturn(Optional.of(email));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void findById_InvalidEmailId_ReturnsError() throws Exception {
    long id = 1L;
    when(emailService.findById(5L)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Email with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_ReturnsEmail() throws Exception {
    Email email = EmailFixture.createEmail();
    EmailResponse response = EmailResponse.fromEntity(email);
    User user = UserFixture.createUser();
    EmailRequest request = EmailRequestFixture.createEmailRequest();
    when(emailService.create(request, user.getEmail())).thenReturn(email);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void create_InvalidRequest_ReturnsError() throws Exception {
    EmailRequest request = EmailRequestFixture.createEmailRequestWithInvalidEmail();
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "to=joke.com, must be a well-formed email address");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidEmailId_ReturnsError() throws Exception {
    EmailRequest request = EmailRequestFixture.createEmailRequest();
    long id = 99L;
    when(emailService.findById(anyLong())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Email with id=99 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesEmail() throws Exception {
    Email email = EmailFixture.createEmail();
    EmailResponse response = EmailResponse.fromEntity(email);
    EmailRequest request = EmailRequestFixture.createEmailRequest();
    long id = 1L;
    when(emailService.findById(id)).thenReturn(Optional.of(email));
    when(emailService.update(email, request)).thenReturn(email);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidEmailId_ReturnsError() throws Exception {
    EmailRequest request = EmailRequestFixture.createEmailRequest();
    long id = 1L;
    when(emailService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Email with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesEmail() throws Exception {
    Email email = EmailFixture.createEmail();
    long id = 1L;
    when(emailService.findById(id)).thenReturn(Optional.of(email));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }
}