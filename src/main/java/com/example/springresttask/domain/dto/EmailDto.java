package com.example.springresttask.domain.dto;


import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

    @NotNull
    private String recipientName;

    @NotNull
    private String emailSubject;

    @NotNull
    private String emailBody;

    @NotNull
    private Boolean isSent = false;
}
