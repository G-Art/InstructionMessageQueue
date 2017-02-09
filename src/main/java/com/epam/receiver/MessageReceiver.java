package com.epam.receiver;

import com.epam.validator.ValidationException;

import java.text.ParseException;


public interface MessageReceiver {
    void receive(String message) throws ValidationException, ParseException;
}
