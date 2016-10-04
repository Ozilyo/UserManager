package com.interfac.usermanager.user.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;


import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.validation.Errors;

/**
 * This annotation validates matching of the fields password and matchingPassword in the annotated class.
 * <p>It enforces the constraint in specified in {@link PasswordsMatchValidator} class. In case of no-match 
 * a default message "Passwords don't match" is added with the {@link Errors} object.</p>
 * 
 * @author Ali Abdalla
 *
 */
@Target({ElementType.TYPE})//,ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
public @interface PasswordsMatch {

    String message() default "Passwords don't match";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
	
}
