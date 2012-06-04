package org.hibernate.validator.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.Email;

public class EmailValidator implements ConstraintValidator<Email, String> {

	public void initialize(Email constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;

		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

		boolean valid = false;

		if (value.getClass().toString().equals(String.class.toString())) {
			valid = ((String) value).matches(emailPattern);
		} else {
			valid = ((Object) value).toString().matches(emailPattern);
		}

		return valid;
	}
}
