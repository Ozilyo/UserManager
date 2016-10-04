package com.interfac.usermanager.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.interfac.usermanager.user.model.User;

/**
 * This class implements {@link ConstraintValidator}. It validates match condition in types annotated with @{@link PasswordsMatch} 
 * annotation. 
 * 
 * @author Ali
 *
 */
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(PasswordsMatch constraintAnnotation) {
	}

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		User user = (User) obj;
		return user.getPassword().equals(user.getMatchingPassword());
	}
}
