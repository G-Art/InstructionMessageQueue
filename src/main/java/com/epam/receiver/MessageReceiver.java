package com.epam.receiver;

import com.epam.validation.ValidationException;


public interface MessageReceiver {
    void receive(String message) throws ValidationException;
}
