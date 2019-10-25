package com.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ValidationService {

    private Validator validator;

    @Autowired
    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public <T> Map<String, String> validate(T t) {

        Map<String, String> errors = new HashMap<>();

        if(t == null) {
            errors.put("ValidationError", "Data is empty");
            return errors;
        }

        Set<ConstraintViolation<T>> constraintViolation = validator.validate(t);

        if(constraintViolation != null && constraintViolation.size() > 0) {
            constraintViolation.stream().forEach(x -> errors.put(x.getPropertyPath().toString() + "Error", x.getMessage()));
        }

        return errors;
    }

}
