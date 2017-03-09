package com.epam.parser;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.message.MessageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.valueOf;
import static java.util.Objects.requireNonNull;


public class InstructionMessageParser {

    private static final int MESSAGE_PARAMETER_COUNT = 6;

    private static final String MESSAGE_PREFIX = "InstructionMessage";
    private static final String MESSAGE_NEW_LINE_CHARACTER = "\n";

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String SPLITTING_REGEXP = " ";

    private static final int INSTRUCTION_PREFIX_POSITION = 0;
    private static final int INSTRUCTION_TYPE_POSITION = 1;
    private static final int PRODUCT_CODE_POSITION = 2;
    private static final int QUANTITY_POSITION = 3;
    private static final int UOM_POSITION = 4;
    private static final int TIMESTAMP_POSITION = 5;

    public InstructionMessage parse(String message) {
        try {
            requireNonNull(message, "Message shouldn't be null");
            String[] splittedMessage = splitMessage(message);
            checkMessage(splittedMessage);
            return createInstructionMessage(splittedMessage);
        } catch (RuntimeException e) {
            throw new MessageParseException("Message: " + message + "can't be parsed due to: "+ e.getClass().getName() + ": " + e.getMessage(), e);
        }
    }

    private InstructionMessage createInstructionMessage(String[] splittedMessage) {
        MessageType messageType = MessageType.valueOf(splittedMessage[INSTRUCTION_TYPE_POSITION]);
        String productCode = splittedMessage[PRODUCT_CODE_POSITION];
        Integer quantity = valueOf(splittedMessage[QUANTITY_POSITION]);
        Integer uom = valueOf(splittedMessage[UOM_POSITION]);
        Date timestamp;
        try {
            timestamp = new SimpleDateFormat(DATE_FORMAT).parse(splittedMessage[TIMESTAMP_POSITION]);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Timestamp in not match the data format: " + DATE_FORMAT, e);
        }

        return new InstructionMessage(messageType, productCode, quantity, uom, timestamp);
    }

    private void checkMessage(String[] message) {
        checkParametersCount(message);
        checkInstructionMessagePrefix(message);
        checkNewLineSymbolIsPresent(message);
    }

    private void checkNewLineSymbolIsPresent(String[] message) {
        if (!message[message.length - 1].endsWith(MESSAGE_NEW_LINE_CHARACTER)) {
            throw new IllegalArgumentException("Message should end with in newline character");
        }
    }

    private void checkInstructionMessagePrefix(String[] message) {
        if (!message[INSTRUCTION_PREFIX_POSITION].equals(MESSAGE_PREFIX)) {
            throw new IllegalArgumentException("Message should start from " + MESSAGE_PREFIX);
        }
    }

    private void checkParametersCount(String[] splittedMessage) {
        if (splittedMessage.length != MESSAGE_PARAMETER_COUNT) {
            throw new IllegalArgumentException("Not all parameters are present in the message. Example: InstructionMessage <InstructionType> <ProductCode> <Quantity> <UOM> <Timestamp>");
        }
    }

    private String[] splitMessage(String message) {
        return message.split(SPLITTING_REGEXP);
    }
}
