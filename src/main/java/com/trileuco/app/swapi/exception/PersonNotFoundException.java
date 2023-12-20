package com.trileuco.app.swapi.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super();
    }

    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}