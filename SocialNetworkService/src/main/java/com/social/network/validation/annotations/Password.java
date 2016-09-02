package com.social.network.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.social.network.validation.PasswordConstraintValidator;

/**
 * Created by Yadykin Andrii May 25, 2016
 *
 */

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target( {  ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String value();
    
    String message() default "{com.social.network.validation.annotations.Password.message}";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
}

