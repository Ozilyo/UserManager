package com.interfac.usermanager.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.interfac.usermanager.user.model.User;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

	@Override
	public void initialize(PasswordsMatch constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		User user = (User) obj;
		return user.getPassword().equals(user.getMatchingPassword());
	}
}
