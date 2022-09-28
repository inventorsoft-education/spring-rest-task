package com.messenger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.api.auth.JwtVerifier;
import com.messenger.domain.UserFixture;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PROTECTED)
public class AbstractControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  JwtVerifier tokenService;

  @MockBean
  UserDetailsService userDetailsService;

  protected static MockHttpServletRequestBuilder auth(MockHttpServletRequestBuilder builder) {
    return builder.header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHJha3V0ZW4uY29tIiwiaXNzIjoidGVzdEByYWt1dGVuLmNvbSIsImV4cCI6NzIwMTY0ODA1MzMxMX0.pI4PBB_FvxO2YWw_QOruFYFH_TJ7s6tyMSiYDFHWsZk");
  }

  @BeforeEach
  void setUp() {
    String username = "test@username.com";
    when(tokenService.verify(any())).thenReturn(username);
    when(userDetailsService.loadUserByUsername(username)).thenReturn(UserFixture.createUser());
  }

}
