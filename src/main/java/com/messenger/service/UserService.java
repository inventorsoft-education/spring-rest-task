package com.messenger.service;

import com.messenger.api.dto.request.UserRequest;
import com.messenger.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  List<User> findAll();

  Optional<User> findById(Long id);

  User save(User user);

  User create(UserRequest request);

  User update(User user, UserRequest request);

  void delete(User user);

  Optional<Long> findIdByEmail(String email);
}
