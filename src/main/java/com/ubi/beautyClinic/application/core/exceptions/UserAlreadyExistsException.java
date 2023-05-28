package com.ubi.beautyClinic.application.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends BusinessLogicException {

    public UserAlreadyExistsException(String message) {

        super(message);
    }
}
