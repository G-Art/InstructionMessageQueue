package com.epam.receiver.impl;

import com.epam.parser.InstructionMessageParser;
import com.epam.queue.InstructionQueue;
import com.epam.queue.message.InstructionMessage;
import com.epam.receiver.MessageReceiver;
import com.epam.validator.InstructionMessageValidator;

import java.text.ParseException;


public class DefaultMessageReceiver implements MessageReceiver {

    private InstructionMessageValidator validator;
    private InstructionQueue queue;
    private InstructionMessageParser parser;

    public DefaultMessageReceiver(InstructionMessageValidator validator, InstructionQueue queue, InstructionMessageParser parser) {
        this.validator = validator;
        this.queue = queue;
        this.parser = parser;
    }

    @Override
    public void receive(String message) throws ParseException {
        InstructionMessage instructionMessage = parser.parse(message);
        validator.validate(instructionMessage);
        queue.enqueue(instructionMessage);
    }


}
