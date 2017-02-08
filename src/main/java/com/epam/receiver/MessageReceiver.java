package com.epam.receiver;

import com.epam.validator.ValidationException;


public interface MessageReceiver {
    void receive(String message) throws ValidationException;
}
