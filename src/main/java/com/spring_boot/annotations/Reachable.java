package com.spring_boot.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReachableConstraints.class)
public @interface Reachable {

    String message() default "Date is unreachable";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};

}