package com.example.homework6.repos;

import com.example.homework6.EmailItem;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepo extends CrudRepository<EmailItem, Integer> {
}
