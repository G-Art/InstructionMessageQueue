package com.epam.receiver.impl;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.MessageReceiver;
import com.epam.validator.InstructionMessageValidator;

import java.text.ParseException;

public class DefaultMessageReceiver implements MessageReceiver {

    private InstructionMessageValidator validator;
    private InstructionQueue queue;

    @Override
    public void receive(String message) throws ParseException {
        queue.enqueue(new InstructionMessage(validator.validate(message)));
    }

    public void setValidator(InstructionMessageValidator validator) {
        this.validator = validator;
    }

    public void setQueue(InstructionQueue queue) {
        this.queue = queue;
    }

}
