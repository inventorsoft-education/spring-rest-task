package com.example.springresttask.domain.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    @Size(min = 2, max = 100, message = "Name should be 2-100 characters long")
    @Column(name = "recipient_name")
    private String recipientName;
    @NotNull
    @Column(name = "email_subject")
    private String emailSubject;
    @NotNull
    @Column(name = "email_body")
    private String emailBody;
    @NotNull
    @Column(name = "is_sent")
    private Boolean isSent = false;

}
