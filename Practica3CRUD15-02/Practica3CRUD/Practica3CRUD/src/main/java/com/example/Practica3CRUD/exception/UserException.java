package com.example.Practica3CRUD.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserException extends ResponseStatusException {
    public UserException(HttpStatus status,String message) {
        super(status,message);
    }
}
