package com.messenger.domain;

public class UserFixture {
  public static User createUser() {
    return User.builder()
        .id(1L)
        .email("company@gmail.com")
        .firstName("firstName")
        .lastName("lastName")
        .password("TestTest")
        .phone("+380502222222")
        .active(true)
        .build();
  }
}
