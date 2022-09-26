package com.messenger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
public class EmailRequest {
  @Email
  @Size(max = 255)
  String from;
  @Email
  @Size(max = 255)
  String to;
  @Email
  @Size( max = 255)
  String cc;
  @Size(min = 1, max = 255)
  String subject;
  @Size(min = 1, max = 255)
  String body;
  @NotNull
  LocalDateTime deliveryDate;
}
