package com.messenger.domain;

import java.time.LocalDateTime;

public class EmailFixture {
  public static Email createEmail(){
    return Email.builder()
        .id(1L)
        .from("user1@gmail.com")
        .to("user2@gmail.com")
        .cc("user3@gmail.com, user4@gmail.com")
        .subject("test")
        .body("We have to test all endpoints")
        .status(Status.PENDING)
        .deliveryDate(LocalDateTime.of(2022,10,10, 9, 0,0))
        .build();
  }
}
