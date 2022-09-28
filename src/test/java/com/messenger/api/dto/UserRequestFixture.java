package com.messenger.api.dto;

import com.messenger.api.dto.request.UserRequest;

public class UserRequestFixture {
  public static UserRequest createUserRequest() {
    return UserRequest.builder()
        .email("company@gmail.com")
        .firstName("firstName")
        .lastName("lastName")
        .password("TestTest")
        .phone("+380502222222")
        .build();
  }
}
