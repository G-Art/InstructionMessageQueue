package com.epam.parsers;

import com.epam.data.InstructionMessage;
import com.epam.queue.MessageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InstructionMessageParser {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private SimpleDateFormat dateFormat;

    public InstructionMessageParser() {
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    public InstructionMessage parse(String message) throws ParseException {

        String[] splittedMessage = message.split(" ");

            InstructionMessage instructionMessage = new InstructionMessage();
            instructionMessage.setInstructionType(MessageType.valueOf(splittedMessage[1]));
            instructionMessage.setProductCode(splittedMessage[2]);
            instructionMessage.setQuantity(Integer.parseInt(splittedMessage[3]));
            instructionMessage.setUom(Integer.parseInt(splittedMessage[4]));
            instructionMessage.setTimestamp(dateFormat.parse(splittedMessage[5]));
            return instructionMessage;
    }


}
