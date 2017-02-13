package com.epam.receiver.impl;

import com.epam.parsers.InstructionMessageParser;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.MessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.ValidationException;

import java.text.ParseException;

public class DefaultMessageReceiver implements MessageReceiver {

    private InstructionMessageParser instructionMessageParser;
    private InstructionMessageValidator validator;
    private InstructionQueue queue;

    public DefaultMessageReceiver(InstructionMessageParser parser, InstructionMessageValidator validator, InstructionQueue queue) {
        this.instructionMessageParser = parser;
        this.validator = validator;
        this.queue = queue;
    }

    @Override
    public void receive(String message) throws ValidationException, ParseException {

        validator.validate(message);
        queue.enqueue(instructionMessageParser.parse(message));
    }

}
