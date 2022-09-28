package com.messenger.service;

import com.messenger.api.dto.UserRequestFixture;
import com.messenger.api.dto.request.UserRequest;
import com.messenger.domain.User;
import com.messenger.domain.UserFixture;
import com.messenger.repository.UserRepository;
import com.messenger.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  UserServiceImpl userService;
  @Captor
  ArgumentCaptor<User> userArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;
  @Mock
  private UserRepository userRepository;


  @Test
  void findAll() {
    List<User> users = List.of(UserFixture.createUser());
    when(userRepository.findAllByActiveTrue()).thenReturn(users);
    List<User> actualUsers = userService.findAll();
    assertThat(actualUsers).isEqualTo(users);
  }

  @Test
  void getUserById_whenValidId() {
    User user = UserFixture.createUser();
    when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(user));
    userService.findById(user.getId());
    verify(userRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    Long id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(user.getId());
  }

  @Test
  void saveUser() {
    User user = UserFixture.createUser();
    UserRequest request = UserRequestFixture.createUserRequest();
    userService.save(user);
    verify(userRepository).save(userArgumentCaptor.capture());
    assertThat(user.getFirstName()).isEqualTo(request.getFirstName());
    assertThat(user.getLastName()).isEqualTo(request.getLastName());
    assertThat(user.getEmail()).isEqualTo(request.getEmail());
  }

  @Test
  void update() {
    User user = UserFixture.createUser();
    UserRequest request = UserRequestFixture.createUserRequest();
    userService.update(user, request);
    verify(userRepository).save(userArgumentCaptor.capture());
    User actualUser = userArgumentCaptor.getValue();
    assertThat(actualUser.getEmail()).isEqualTo((request.getEmail()));
  }

  @Test
  void deleteUser() {
    User user = UserFixture.createUser();
    userService.delete(user);
    verify(userRepository).save(userArgumentCaptor.capture());
    User actualUser = userArgumentCaptor.getValue();
    assertThat(actualUser).isEqualTo(user);
    assertThat(actualUser.getActive()).isFalse();
  }
}
