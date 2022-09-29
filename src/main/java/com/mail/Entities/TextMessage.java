package com.mail.Entities;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

import java.time.LocalTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextMessage {
    static int counter = 0;

    int id;
    String title;
    @NonNull
    String from;
    @NonNull
    String to;
    String message;
    LocalTime time;

    {
        counter++;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    public String getFrom() {
        return from;
    }

    @NonNull
    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFrom(@NonNull String from) {
        this.from = from;
    }

    public void setTo(@NonNull String to) {
        this.to = to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TextMessage(String title, @NonNull String from, @NonNull String to, String message) {
        this.id = counter;
        this.title = title;
        this.from = from;
        this.to = to;
        this.message = message;
        this.time = LocalTime.now();
    }

    public void update(TextMessage text) {
        this.from = from;
        this.message = message;
        this.title = title;
        this.to = to;
    }
}
