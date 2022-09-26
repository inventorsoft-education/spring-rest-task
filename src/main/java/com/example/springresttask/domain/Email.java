package com.example.springresttask.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @javax.validation.constraints.Email
    @NotNull
    @Size(min = 2, max = 100, message = "Name should be 2-100 characters long")
    @Column(name = "recipient_name")
    private String recipientName;
    @NotNull
    @Column(name = "email_subject")
    private String emailSubject;
    @NotNull
    @Column(name = "email_body")
    private String emailBody;
    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
    //todo:check as best
    @NotNull
    @Column(name = "is_sent")
    private Boolean isSent = false;

}