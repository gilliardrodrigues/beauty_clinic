package com.ubi.beautyClinic.application.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends BusinessLogicException {

    public ObjectNotFoundException(String message) {

        super(message);
    }
}
