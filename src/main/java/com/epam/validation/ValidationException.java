package com.epam.validation;

public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
