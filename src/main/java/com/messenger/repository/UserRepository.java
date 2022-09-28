package com.messenger.repository;

import com.messenger.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  User findOneByEmailAndActiveTrue(String email);

  List<User> findAllByActiveTrue();

  Optional<User> findByIdAndActiveTrue(Long id);

  @Query(value = "select id from users where email=? and active=true", nativeQuery = true)
  Optional<Long> findIdByEmailAndActiveTrue(String email);

}
