package com.messenger.repository;

import com.messenger.domain.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EmailRepository extends CrudRepository<Email, Long> {
  List<Email> findAll();

}
