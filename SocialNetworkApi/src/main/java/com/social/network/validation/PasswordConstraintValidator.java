package com.social.network.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.social.network.validation.annotations.Password;

/**
 * Created by Yadykin Andrii May 25, 2016
 *
 */

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    private final static Logger logger = LoggerFactory.getLogger(PasswordConstraintValidator.class);
    private String regex;

    @Override
    public void initialize(Password password) {
        this.regex = password.value();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.debug("PasswordConstraintValidator -> isValid regex = {}", regex);
        if (Pattern.compile(regex).matcher(value).matches()) {
            return true;
        } else {
            return false;
        }
    }

}
