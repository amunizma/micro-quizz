package com.github.amunizma.quizz.rest.game.validation;

import java.util.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    	boolean valid = false;
        try {
        	if (value != null && !"".equals(value.trim())) {
	            UUID.fromString(value);
	            valid = true;
        	}
        } catch (IllegalArgumentException e) {
        }
        return valid;
    }
}
