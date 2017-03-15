package com.epam.parser;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.message.MessageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.valueOf;


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
        String[] splittedMessage = splitMessage(message);
        checkMessage(splittedMessage);
        return createInstructionMessage(splittedMessage);
    }

    private InstructionMessage createInstructionMessage(String[] splittedMessage) {
        MessageType messageType = retrieveMessageType(splittedMessage);
        String productCode = splittedMessage[PRODUCT_CODE_POSITION];
        Integer quantity = retrieveQuantity(splittedMessage);
        Integer uom = retrieveUom(splittedMessage);
        Date timestamp;
        try {
            timestamp = new SimpleDateFormat(DATE_FORMAT).parse(splittedMessage[TIMESTAMP_POSITION]);
        } catch (ParseException e) {
            throw new MessageParseException("Timestamp in not match the data format: " + DATE_FORMAT, e);
        }

        return new InstructionMessage(messageType, productCode, quantity, uom, timestamp);
    }

    private Integer retrieveUom(String[] splittedMessage) {
        try {
            return valueOf(splittedMessage[UOM_POSITION]);
        } catch (RuntimeException ex) {
            throw new MessageParseException("Uom parameter is not a integer", ex);
        }
    }

    private Integer retrieveQuantity(String[] splittedMessage) {
        try {
            return valueOf(splittedMessage[QUANTITY_POSITION]);
        } catch (RuntimeException ex) {
            throw new MessageParseException("Product quantity parameter is not a integer", ex);
        }
    }

    private MessageType retrieveMessageType(String[] splittedMessage) {
        try {
            return MessageType.valueOf(splittedMessage[INSTRUCTION_TYPE_POSITION]);
        } catch (RuntimeException ex) {
            throw new MessageParseException("Message type is wrong. Expected [ A,B,C,D ]", ex);
        }
    }

    private void checkMessage(String[] message) {
        checkParametersCount(message);
        checkInstructionMessagePrefix(message);
        checkSuffixIsPresent(message);
    }

    private void checkSuffixIsPresent(String[] message) {
        String lastParameter = message[message.length - 1];

        if (!lastParameter.endsWith(MESSAGE_NEW_LINE_CHARACTER) || lastParameter.substring(0, lastParameter.length() - MESSAGE_NEW_LINE_CHARACTER.length()).endsWith(MESSAGE_NEW_LINE_CHARACTER)) {
            throw new MessageParseException("Message should end with one newline character");
        }
    }

    private void checkInstructionMessagePrefix(String[] message) {
        if (!message[INSTRUCTION_PREFIX_POSITION].equals(MESSAGE_PREFIX)) {
            throw new MessageParseException("Message should start from " + MESSAGE_PREFIX);
        }
    }

    private void checkParametersCount(String[] splittedMessage) {
        if (splittedMessage.length != MESSAGE_PARAMETER_COUNT) {
            throw new MessageParseException("Not all parameters are present in the message. Example: InstructionMessage <InstructionType> <ProductCode> <Quantity> <UOM> <Timestamp>");
        }
    }

    private String[] splitMessage(String message) {
        if (message == null || message.isEmpty()) {
            throw new MessageParseException("Message should be null or empty");
        }
        return message.split(SPLITTING_REGEXP);
    }
}
