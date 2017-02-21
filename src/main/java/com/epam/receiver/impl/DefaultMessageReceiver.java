package com.epam.receiver.impl;

import com.epam.queue.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.queue.MessageType;
import com.epam.receiver.MessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DefaultMessageReceiver implements MessageReceiver {

    private String dateFormat;
    private InstructionMessageValidator validator;
    private InstructionQueue queue;

    @Override
    public void receive(String message) throws ValidationException, ParseException {
        String[] splittedMessage = validator.validate(message);
        queue.enqueue(convert(splittedMessage));
    }

    public InstructionMessage convert(String[] splittedMessage) throws ParseException {

        InstructionMessage instructionMessage = new InstructionMessage();
        instructionMessage.setInstructionType(MessageType.valueOf(splittedMessage[1]));
        instructionMessage.setProductCode(splittedMessage[2]);
        instructionMessage.setQuantity(Integer.parseInt(splittedMessage[3]));
        instructionMessage.setUom(Integer.parseInt(splittedMessage[4]));
        instructionMessage.setTimestamp(new SimpleDateFormat(dateFormat).parse(splittedMessage[5]));

        return instructionMessage;
    }

    public void setValidator(InstructionMessageValidator validator) {
        this.validator = validator;
    }

    public void setQueue(InstructionQueue queue) {
        this.queue = queue;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
