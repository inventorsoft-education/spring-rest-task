package com.example.demo.model;

import java.util.Date;

public class Message {

    private long id;

    private String subject;

    private String emailTo;

    private String message;

    private long futureDate;

    private Status status;

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFutureDate(long futureDate) {
        this.futureDate = futureDate;
    }

    public long getFutureDate() {
        return futureDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
