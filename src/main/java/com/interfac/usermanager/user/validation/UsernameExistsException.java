package com.interfac.usermanager.user.validation;

/**
 * This is a custom exception thrown when the user to be added has the same username as another one in the database.
 * 
 * @author Ali Abdalla
 *
 */
@SuppressWarnings("serial")
public class UsernameExistsException extends Throwable{

    /**
     * Constructor with <code>message</code> parameter.
     * @param message
     */
    public UsernameExistsException(final String message) {
        super(message);
    }
}
