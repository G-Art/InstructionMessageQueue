package com.epam.receiver.impl;

import com.epam.queue.InstructionQueue;
import com.epam.receiver.MessageReceiver;
import com.epam.validator.InstructionMessageValidator;

import java.text.ParseException;

import static com.epam.queue.message.InstructionMessage.MessageBuilder.build;

public class DefaultMessageReceiver implements MessageReceiver {

    private final static String SPRITTING_REGEXP = " ";

    private InstructionMessageValidator validator;
    private InstructionQueue queue;

    @Override
    public void receive(String message) throws ParseException {
        String[] splittedMessage = message.split(SPRITTING_REGEXP);
        validator.validate(splittedMessage);
        queue.enqueue(build(splittedMessage));
    }

    public void setValidator(InstructionMessageValidator validator) {
        this.validator = validator;
    }

    public void setQueue(InstructionQueue queue) {
        this.queue = queue;
    }

}
