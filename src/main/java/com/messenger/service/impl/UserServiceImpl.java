package com.messenger.service.impl;

import com.messenger.api.dto.request.UserRequest;
import com.messenger.domain.User;
import com.messenger.repository.UserRepository;
import com.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
  UserRepository userRepository;

  PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAll() {
    return userRepository.findAllByActiveTrue();
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findByIdAndActiveTrue(id);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public User create(UserRequest request) {
    return save(User.create(request, passwordEncoder.encode(request.getPassword())));
  }

  @Override
  public User update(User user, UserRequest request) {
    user.update(request);
    return save(user);
  }

  @Override
  public void delete(User user) {
    user.delete();
   save(user);
  }

  @Override
  public Optional<Long> findIdByEmail(String email) {
    return userRepository.findIdByEmailAndActiveTrue(email);
  }
}
