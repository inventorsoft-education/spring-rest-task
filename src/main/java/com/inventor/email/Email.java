package com.inventor.email;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@Setter
public class Email implements Serializable {
    private String receiver;
    private String subject;
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm", timezone = "EET")
    private LocalDateTime dateOfReceiving;
    private Boolean send;

    Email() {
        receiver = "victordoom@i.ua";
        subject = "Run";
        text = "Runs";
        dateOfReceiving = LocalDateTime.of(2022,12,10,20,15);
        send = false;
    }

    @Override
    public String toString() {
        return "\nReceiver: " + receiver + "\nSubject: " + subject + "\nText: " + text + "\nDate of Receiving: " + dateOfReceiving + "\nis Send: " + send + "\n";
    }


}
