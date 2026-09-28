package com.enigma.loan.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserRoleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserRole {
    String message() default "Format role tidak sesuai";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
