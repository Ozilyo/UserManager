package com.interfac.usermanager.util;

@SuppressWarnings("serial")
public class UsernameExistsException extends Throwable{

    public UsernameExistsException(final String message) {
        super(message);
    }
}
