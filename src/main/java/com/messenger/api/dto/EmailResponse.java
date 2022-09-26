package com.messenger.api.dto;

import com.messenger.domain.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailResponse {
  Long id;
  String from;
  String to;
  String cc;
  String subject;
  String body;
  LocalDateTime deliveryDate;

  public static EmailResponse fromEntity(Email client) {
    return EmailResponse.builder()
        .id(client.getId())
        .from(client.getFrom())
        .to(client.getTo())
        .cc(client.getCc())
        .subject(client.getSubject())
        .body(client.getBody())
        .deliveryDate(client.getDeliveryDate())
        .build();
  }
}
