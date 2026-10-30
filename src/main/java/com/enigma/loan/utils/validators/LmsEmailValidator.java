package com.enigma.loan.utils.validators;

import com.enigma.loan.dto.request.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LmsEmailValidator implements ConstraintValidator<ValidLmsEmail, UserRequest> {
    @Override
    public boolean isValid(
            UserRequest request,
            ConstraintValidatorContext context
    ) {
        if(request == null) {
            return true;
        }

        if(request.getUsername() == null || request.getRole() == null) {
            return true;
        }

        return true;
    }
}
