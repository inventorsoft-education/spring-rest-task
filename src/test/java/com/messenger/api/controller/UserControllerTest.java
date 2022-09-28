package com.messenger.api.controller;

import com.messenger.api.dto.UserRequestFixture;
import com.messenger.api.dto.request.UserRequest;
import com.messenger.api.dto.response.ErrorResponse;
import com.messenger.api.dto.response.UserResponse;
import com.messenger.domain.User;
import com.messenger.domain.UserFixture;
import com.messenger.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.messenger.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractControllerTest {
  public static final String URL = "/api/v1/users";

  @MockBean
  UserService userService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsUsers() throws Exception {
    User user = UserFixture.createUser();
    List<UserResponse> userResponses = List.of(UserResponse.fromEntity(user));
    when(userService.findAll()).thenReturn(List.of(user));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(userResponses)));
  }

  @Test
  void get_ValidUserId_ReturnsUser() throws Exception {
    User user = UserFixture.createUser();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.ofNullable(user));
    UserResponse userResponse = UserResponse.fromEntity(Objects.requireNonNull(user));
    this.mockMvc.perform(auth(get(URL + "/" + userId)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(userResponse)));
  }

  @Test
  void get_InvalidUserId_ReturnsError() throws Exception {
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "User with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + userId)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesUser() throws Exception {
    UserRequest request = UserRequestFixture.createUserRequest();
    when(userService.findIdByEmail(request.getEmail())).thenReturn(Optional.empty());
    User user = UserFixture.createUser();
    UserResponse userResponse = UserResponse.fromEntity(user);
    when(userService.create(request)).thenReturn(user);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(userResponse)));
  }

  @Test
  void create_InvalidRequest_ReturnsError() throws Exception {
    UserRequest request = UserRequestFixture.createUserRequest();
    when(userService.findIdByEmail(request.getEmail())).thenReturn(Optional.of(1L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "User with param: " + request.getEmail() + " already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidUserId_ReturnsError() throws Exception {
    UserRequest request = UserRequestFixture.createUserRequest();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "User with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + userId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidRequest_ReturnsError() throws Exception {
    User user = UserFixture.createUser();
    UserRequest request = UserRequestFixture.createUserRequest();
    long id = 1L;
    when(userService.findById(id)).thenReturn(Optional.of(user));
    when(userService.findIdByEmail(request.getEmail())).thenReturn(Optional.of(12L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "User with param: " + request.getEmail() + " already exists");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesUser() throws Exception {
    UserRequest request = UserRequestFixture.createUserRequest();
    User user = UserFixture.createUser();
    long userId = 1L;
    UserResponse userResponse = UserResponse.fromEntity(user);
    when(userService.findById(userId)).thenReturn(Optional.of(user));
    when(userService.update(user, request)).thenReturn(user);
    this.mockMvc.perform(auth(put(URL + "/" + userId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(userResponse)));
  }

  @Test
  void delete_InvalidUserId_ReturnsError() throws Exception {
    UserRequest request = UserRequestFixture.createUserRequest();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "User with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + userId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesUser() throws Exception {
    User user = UserFixture.createUser();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.of(user));
    this.mockMvc.perform(auth(delete(URL + "/" + userId)))
        .andExpect(status().is2xxSuccessful());
  }
}
