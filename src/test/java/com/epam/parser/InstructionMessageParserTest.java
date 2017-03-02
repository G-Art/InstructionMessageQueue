package com.epam.parser;

import com.epam.queue.message.InstructionMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstructionMessageParserTest {

    private static final String CORRECT_MESSAGE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z\n";

    private static final String MESSAGE_WITHOUT_NEWLINE_CHARACTER = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_WRONG_PREFIX = "Instruction A MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_MISSING_PARAMETERS = "InstructionMessage 2015-03-05T10:04:56.012Z\n";

    private InstructionMessageParser instructionMessageParser;

    @Before
    public void setUp() throws Exception {
        instructionMessageParser = new InstructionMessageParser();
    }

    @Test
    public void shouldCorrectlyParseMessageString() throws Exception {
        InstructionMessage parsedMessage = instructionMessageParser.parse(CORRECT_MESSAGE);
        assertNotNull("Instruction message shouldn't be null", parsedMessage);
        assertNotNull("Instruction type shouldn't be null", parsedMessage.getInstructionType());
        assertNotNull("Product code shouldn't be null", parsedMessage.getProductCode());
        assertNotNull("Quantity shouldn't be null", parsedMessage.getQuantity());
        assertNotNull("Uom shouldn't be null", parsedMessage.getUom());
        assertNotNull("Timestamp shouldn't be null", parsedMessage.getTimestamp());
    }

    @Test(expected = InstructionMessageParseException.class)
    public void shouldThrowExceptionWhenNewLineCharacterMissed(){
        instructionMessageParser.parse(MESSAGE_WITHOUT_NEWLINE_CHARACTER);
    }

    @Test(expected = InstructionMessageParseException.class)
    public void shouldThrowExceptionWhenPrefixWrong(){
        instructionMessageParser.parse(MESSAGE_WITH_WRONG_PREFIX);
    }

    @Test(expected = InstructionMessageParseException.class)
    public void shouldThrowExceptionWhenParametersAreMissed() {
        instructionMessageParser.parse(MESSAGE_WITH_MISSING_PARAMETERS);
    }
}