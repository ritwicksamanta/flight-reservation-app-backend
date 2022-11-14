package com.rx.security.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Password must contain one upper case, one lower case, one digit, one special character and length should be 10";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
