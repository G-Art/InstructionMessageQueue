package com.epam.parsers.impl;

import com.epam.Constants;
import com.epam.data.InstructionMessage;
import com.epam.parsers.Parser;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InstructionMessageParser implements Parser<InstructionMessage, String>{

    private static final String MESSAGE_PREFIX = "InstructionMessage";

    private SimpleDateFormat dateFormat;

    public InstructionMessageParser() {
        this.dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
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
            return new InstructionMessage(splittedMessage[1], splittedMessage[2],
                                                        Integer.parseInt(splittedMessage[3]),
                                                        Integer.parseInt(splittedMessage[4]),
                                                        dateFormat.parse(splittedMessage[5]));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
