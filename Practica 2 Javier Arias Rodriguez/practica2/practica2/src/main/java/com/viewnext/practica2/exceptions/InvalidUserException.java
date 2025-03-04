package com.viewnext.practica2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserException extends ResponseStatusException {

    public InvalidUserException(HttpStatus status, String reason) {
        super(status, reason);
        getBody().setDetail(reason);
        getBody().setStatus(status.value());
    }

    public InvalidUserException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
        getBody().setDetail(reason);
        getBody().setStatus(HttpStatus.BAD_REQUEST.value());

    }

}
