package com.epam.parser;

public class MessageParseException extends RuntimeException {

    public MessageParseException(String message) {
        super(message);
    }

    public MessageParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
