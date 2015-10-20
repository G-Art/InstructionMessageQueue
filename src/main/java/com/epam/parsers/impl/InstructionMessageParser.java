package com.epam.parsers.impl;

import com.epam.data.InstructionMessage;
import com.epam.parsers.Parser;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InstructionMessageParser implements Parser<InstructionMessage, String>{

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String MESSAGE_PREFIX = "InstructionMessage";

    private SimpleDateFormat dateFormat;

    public InstructionMessageParser() {
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public InstructionMessage parse(String message) {
        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Error message shouldn't be empty or null ");
        }

        if (!message.startsWith(MESSAGE_PREFIX)) {
            throw new IllegalArgumentException("Error message should start with: (" + MESSAGE_PREFIX + ")");
        }

        String[] splittedMessage = message.split(" ");

        try {
            InstructionMessage instructionMessage = new InstructionMessage();
            instructionMessage.setInstructionType(splittedMessage[1]);
            instructionMessage.setProductCode(splittedMessage[2]);
            instructionMessage.setQuantity(Integer.parseInt(splittedMessage[3]));
            instructionMessage.setUom(Integer.parseInt(splittedMessage[4]));
            instructionMessage.setTimestamp(dateFormat.parse(splittedMessage[5]));
            return instructionMessage;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
