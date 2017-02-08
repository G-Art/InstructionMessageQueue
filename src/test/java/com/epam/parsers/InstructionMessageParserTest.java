package com.epam.parsers;

import com.epam.data.InstructionMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class InstructionMessageParserTest {

    private static final String CORRECT_MESSAGE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private InstructionMessageParser messageParser;

    @Before
    public void setUp() throws Exception {
        messageParser = new InstructionMessageParser();
    }

    @Test
    public void shouldReturnCorrectInstructionMessage() throws ParseException {
        InstructionMessage instructionMessage = messageParser.parse(CORRECT_MESSAGE);
        assertEquals("A", instructionMessage.getInstructionType());
        assertEquals("MZ89", instructionMessage.getProductCode());
        assertEquals(5678, instructionMessage.getQuantity());
        assertEquals(50, instructionMessage.getUom());
        assertEquals(dateFormat.parse("2015-03-05T10:04:56.012Z"), instructionMessage.getTimestamp());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfInstructionMessagePrefixIsEmpty() {
        messageParser.parse("A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfWrongInstructionMessagePrefix() {
        messageParser.parse("InstructionMessage123 A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfInstructionMessageIsEmpty() {
        messageParser.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfInstructionMessageIsNull() {
        messageParser.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfTimestampIsEmpty() {
        messageParser.parse("InstructionMessage123 A MZ89 5678 50");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfWrongTimestampFormat() {
        messageParser.parse("InstructionMessage123 A MZ89 5678 50 2015/03/05T10:04:56.012Z");
    }
}
