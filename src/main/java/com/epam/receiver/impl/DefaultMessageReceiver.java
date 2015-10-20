package com.epam.receiver.impl;

import com.epam.data.InstructionMessage;
import com.epam.parsers.Parser;
import com.epam.parsers.impl.InstructionMessageParser;
import com.epam.queue.InstructionQueue;
import com.epam.queue.impl.DefaultInstructionQueue;
import com.epam.receiver.MessageReceiver;
import com.epam.validation.ValidationException;
import com.epam.validation.Validator;
import com.epam.validation.impl.InstructionMessageValidator;

public class DefaultMessageReceiver implements MessageReceiver {


    private Parser<InstructionMessage, String> instructionMessageParser;
    private Validator<InstructionMessage>      validator;
    private InstructionQueue                   queue;

    public DefaultMessageReceiver() {
        this.instructionMessageParser = new InstructionMessageParser();
        this.validator = new InstructionMessageValidator();
        this.queue = new DefaultInstructionQueue();
    }

    @Override
    public void receive(String message) throws ValidationException {

        InstructionMessage instructionMessage = instructionMessageParser.parse(message);

        validator.validate(instructionMessage);

        queue.enqueue(instructionMessage);
    }

}
