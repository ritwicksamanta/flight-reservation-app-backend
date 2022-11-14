package com.rx.security.validators;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.error(value);
        boolean result = value.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[a-zA-Z0-9@$!%*?&]{10}") ;
        log.error("{}",result);
        return result;
    }
}
