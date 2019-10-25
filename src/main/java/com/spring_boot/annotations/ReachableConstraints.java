package com.spring_boot.annotations;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ReachableConstraints implements ConstraintValidator<Reachable, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value != null && value.isAfter(LocalDateTime.now());
    }
}