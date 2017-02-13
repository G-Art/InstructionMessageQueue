package com.epam.parsers;

import com.epam.data.InstructionMessage;
import com.epam.queue.MessageType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.epam.queue.MessageType.A;
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
    public void shouldCorrectlyParseInstructionMessage() throws ParseException {
        InstructionMessage instructionMessage = messageParser.parse(CORRECT_MESSAGE);

        assertEquals(A, instructionMessage.getInstructionType());
        assertEquals("MZ89", instructionMessage.getProductCode());
        assertEquals(5678, instructionMessage.getQuantity());
        assertEquals(50, instructionMessage.getUom());
        assertEquals(dateFormat.parse("2015-03-05T10:04:56.012Z"), instructionMessage.getTimestamp());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfInstructionMessagePrefixIsEmpty() throws ParseException {
        messageParser.parse("A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowExceptionIfInstructionMessageIsEmpty() throws ParseException {
        messageParser.parse("");
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfInstructionMessageIsNull() throws ParseException {
        messageParser.parse(null);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowExceptionIfTimestampIsEmpty() throws ParseException {
        messageParser.parse("InstructionMessage A MZ89 5678 50");
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionIfWrongTimestampFormat() throws ParseException {
        messageParser.parse("InstructionMessage A MZ89 5678 50 2015/03/05T10:04:56.012Z");
    }
}
