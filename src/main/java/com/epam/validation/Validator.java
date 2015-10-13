package com.epam.validation;

public interface Validator<E> {
    void validate(E o) throws ValidationException;
}
