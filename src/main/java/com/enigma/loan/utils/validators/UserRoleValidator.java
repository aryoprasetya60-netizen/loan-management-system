package com.enigma.loan.utils.validators;

import com.enigma.loan.utils.enums.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserRoleValidator implements ConstraintValidator<ValidUserRole, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ){
      if (value == null  || value.isBlank()) {
          return true;
      }

      return UserRole.isValid(value.toUpperCase());
    }
}
