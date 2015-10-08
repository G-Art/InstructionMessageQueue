package com.epam.validation;

public interface Validator {
    boolean validate(Object o) throws ValidationException;
}
