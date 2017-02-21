package com.epam.receiver;

import com.epam.queue.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.impl.DefaultMessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.epam.queue.MessageType.A;
import static org.junit.Assert.assertEquals;

public class DefaultMessageReceiverTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private DefaultMessageReceiver messageReceiver;

    @Before
    public void setUp() throws Exception {
        InstructionMessageValidator instructionMessageValidator = new InstructionMessageValidator();
        instructionMessageValidator.setDateFormat(DATE_FORMAT);

        messageReceiver = new DefaultMessageReceiver();
        messageReceiver.setDateFormat(DATE_FORMAT);
        messageReceiver.setValidator(instructionMessageValidator);
        messageReceiver.setQueue(new InstructionQueue());
    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test
    public void shouldAcceptWhenReceiveTwoEqualsCorrectMessages() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test
    public void shouldCorrectlyParseInstructionMessage() throws ParseException {
        InstructionMessage instructionMessage = messageReceiver.convert("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z".split(" "));

        assertEquals(A, instructionMessage.getInstructionType());
        assertEquals("MZ89", instructionMessage.getProductCode());
        assertEquals(5678, instructionMessage.getQuantity());
        assertEquals(50, instructionMessage.getUom());
        assertEquals(new SimpleDateFormat(DATE_FORMAT).parse("2015-03-05T10:04:56.012Z"), instructionMessage.getTimestamp());
    }


    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectPrefix() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionA MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutPrefix() throws ValidationException, ParseException {
        messageReceiver.receive(" A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectInstructionCode() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutMessageType() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutProductCode() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWhereProductCodeInLowerCase() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A mz89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWhereProductCodeWithoutDigits() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutQuantity() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutUOM() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUomGraterThan255() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 256 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUomLessThan0() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 -1 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutTimestamp() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectTimestampFormat() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015/03/05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageIsNull() throws ValidationException, ParseException {
        messageReceiver.receive(null);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageIsEmptyString() throws ValidationException, ParseException {
        messageReceiver.receive("");
    }
}