package com.ubi.beautyClinic.application.core.exceptions;

public class UserAlreadyExistsException extends BusinessLogicException {

    public UserAlreadyExistsException(String message) {

        super(message);
    }
}
