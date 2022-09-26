package com.messenger.api.dto;

import java.time.LocalDateTime;

public class EmailRequestFixture {
  public static EmailRequest createEmailRequest(){
    return EmailRequest.builder()
        .from("user1@gmail.com")
        .to("user2@gmail.com")
        .cc("user3@gmail.com")
        .subject("test")
        .body("We have to test all endpoints")
        .deliveryDate(LocalDateTime.of(2022,10,10, 9, 0,0))
        .build();
  }
  public static EmailRequest createEmailRequestWithInvalidEmail(){
    return EmailRequest.builder()
        .from("joke.com")
        .to("user2@gmail.com")
        .cc("user3@gmail.com")
        .subject("test")
        .body("We have to test all endpoints")
        .deliveryDate(LocalDateTime.of(2022,10,10, 9, 0,0))
        .build();
  }
}
