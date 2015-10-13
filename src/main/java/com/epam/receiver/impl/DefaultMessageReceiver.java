package com.epam.receiver.impl;

import com.epam.Constants;
import com.epam.data.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.queue.impl.DefaultInstructionQueue;
import com.epam.receiver.MessageReceiver;
import com.epam.validation.ValidationException;
import com.epam.validation.Validator;
import com.epam.validation.impl.InstructionMessageValidator;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DefaultMessageReceiver implements MessageReceiver {

    private static final String MESSAGE_PREFIX = "InstructionMessage";

    private SimpleDateFormat dateFormat;

    private Validator<InstructionMessage> validator;
    private InstructionQueue              queue;

    public DefaultMessageReceiver() {
        this.dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        this.validator = new InstructionMessageValidator();
        this.queue = new DefaultInstructionQueue();
    }

    @Override
    public void receive(String message) throws ValidationException {
        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Error message shouldn't be empty or null ");
        }

        if (!message.startsWith(MESSAGE_PREFIX)) {
            throw new IllegalArgumentException("Error message should start with: (" + MESSAGE_PREFIX + ")");
        }

        String[] splittedMessage = message.split(" ");

        InstructionMessage instructionMessage = null;
        try {
            instructionMessage = new InstructionMessage(splittedMessage[1], splittedMessage[2],
                                                        Integer.parseInt(splittedMessage[3]),
                                                        Integer.parseInt(splittedMessage[4]),
                                                        dateFormat.parse(splittedMessage[5]));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
        validator.validate(instructionMessage);

        queue.enqueue(instructionMessage);
    }

}
