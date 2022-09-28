package com.messenger.api.controller;

import com.messenger.api.dto.request.UserRequest;
import com.messenger.api.dto.response.UserResponse;
import com.messenger.domain.User;
import com.messenger.exception.AlreadyExistsException;
import com.messenger.exception.NotFoundException;
import com.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {
  UserService userService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<UserResponse> getAll() {
    return userService.findAll().stream()
        .map(UserResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public UserResponse get(@PathVariable Long id) {
    return userService.findById(id)
        .map(UserResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("User", id));
  }

  @PostMapping
  public UserResponse create(@Valid @RequestBody UserRequest request) {
    log.info("Received user registration request {}", request);
    String email = request.getEmail();
    if (userService.findIdByEmail(email).isPresent()) {
      throw new AlreadyExistsException("User", email);
    }
    User newUser = userService.create(request);
    log.info("Created new user {}", request.getEmail());
    return UserResponse.fromEntity(newUser);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public UserResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
    User user = userService.findById(id)
        .orElseThrow(() -> new NotFoundException("User", id));
    Optional<Long> foundId = userService.findIdByEmail(request.getEmail());
    if (foundId.isPresent() && !foundId.get().equals(id)) {
      throw new AlreadyExistsException("User", request.getEmail());
    }
    log.info("Updated user {}", user.getEmail());
    return UserResponse.fromEntity(userService.update(user, request));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    User user = userService.findById(id)
        .orElseThrow(() -> new NotFoundException("User", id));
    log.info("Deleted user {}", user.getEmail());
    userService.delete(user);
  }
}
