package com.messenger.domain;

import com.messenger.api.dto.request.EmailRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emails")
@FieldDefaults(level = PRIVATE)
@Builder
public class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;
  @Column(name = "email_from")
  String from;
  @Column(name = "email_to")
  String to;
  @Column(name = "email_cc")
  String cc;
  @Column
  String subject;
  @Column
  String body;

  @Column
  @Builder.Default
  Status status = Status.PENDING;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  LocalDateTime deliveryDate;

  public static Email create(EmailRequest request, String userEmail) {
    return Email.builder()
        .from(userEmail)
        .to(request.getTo())
        .cc(request.getCc())
        .subject(request.getSubject())
        .body(request.getBody())
        .deliveryDate(request.getDeliveryDate())
        .build();
  }

  public void update(EmailRequest request) {
    to = request.getTo();
    cc = request.getCc();
    subject = request.getSubject();
    body = request.getBody();
    deliveryDate = request.getDeliveryDate();
  }
}
