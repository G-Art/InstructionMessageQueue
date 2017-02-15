package com.epam.receiver;

import com.epam.parsers.InstructionMessageParser;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.impl.DefaultMessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

public class DefaultMessageReceiverTest {

    private MessageReceiver messageReceiver;

    @Before
    public void setUp() throws Exception {
        messageReceiver = new DefaultMessageReceiver(new InstructionMessageParser(), new InstructionMessageValidator(), new InstructionQueue());
    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test
    public void shouldAcceptWhenIfReceiveTwoCorrectMessages() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
        messageReceiver.receive("InstructionMessage B DT89 10 5 2016-07-06T11:04:06.022Z");
    }

    @Test
    public void shouldAcceptWhenIfReceiveTwoEquilsCorrectMessages() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectPrefix() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionA MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectInstructionCode() throws ValidationException, ParseException {
        messageReceiver.receive("InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageIsNull() throws ValidationException, ParseException {
        messageReceiver.receive(null);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageIsEmptyString() throws ValidationException, ParseException {
        messageReceiver.receive("");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutPrefix() throws ValidationException, ParseException {
        messageReceiver.receive(" A MZ89 5678 50 2015-03-05T10:04:56.012Z");
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
}