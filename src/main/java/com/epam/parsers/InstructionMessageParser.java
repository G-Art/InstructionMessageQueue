package com.epam.parsers;

import com.epam.data.InstructionMessage;
import com.epam.queue.MessageType;
import com.epam.utils.DateTimeUtils;

import java.text.ParseException;

public class InstructionMessageParser {

    public InstructionMessage parse(String message) throws ParseException {

        String[] splittedMessage = message.split(" ");

        InstructionMessage instructionMessage = new InstructionMessage();
        instructionMessage.setInstructionType(MessageType.valueOf(splittedMessage[1]));
        instructionMessage.setProductCode(splittedMessage[2]);
        instructionMessage.setQuantity(Integer.parseInt(splittedMessage[3]));
        instructionMessage.setUom(Integer.parseInt(splittedMessage[4]));
        instructionMessage.setTimestamp(DateTimeUtils.parse(splittedMessage[5]));

        return instructionMessage;
    }


}
