package com.epam.receiver.impl;

import com.epam.data.InstructionMessage;
import com.epam.parsers.InstructionMessageParser;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.MessageReceiver;
import com.epam.validator.ValidationException;
import com.epam.validator.InstructionMessageValidator;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Optional;

public class DefaultMessageReceiver implements MessageReceiver {


    private static final String MESSAGE_PREFIX = "InstructionMessage";

    private InstructionMessageParser instructionMessageParser;
    private InstructionMessageValidator      validator;
    private InstructionQueue                   queue;

    public DefaultMessageReceiver(InstructionMessageParser parser, InstructionMessageValidator validator, InstructionQueue queue) {
        this.instructionMessageParser = parser;
        this.validator = validator;
        this.queue = queue;
    }

    @Override
    public void receive(String message) throws ValidationException, ParseException {

        checkInstructionMessagePrefix(message);

        InstructionMessage instructionMessage = instructionMessageParser.parse(message);

        validator.validate(instructionMessage);

        queue.enqueue(instructionMessage);
    }

    private void checkInstructionMessagePrefix(String message) {
        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Message shouldn't be empty or null ");
        }

        String instructionMessage = message.substring(0, message.indexOf(' '));

        if (!instructionMessage.equals(MESSAGE_PREFIX)) {
            throw new IllegalArgumentException("Message should start with: (" + MESSAGE_PREFIX + ")");
        }
    }
}
