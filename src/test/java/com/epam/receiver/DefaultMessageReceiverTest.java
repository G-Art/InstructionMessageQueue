package com.epam.receiver;

import com.epam.queue.InstructionQueue;
import com.epam.queue.message.InstructionMessage;
import com.epam.receiver.impl.DefaultMessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.epam.queue.message.InstructionMessage.DATE_FORMAT;
import static com.epam.queue.message.InstructionMessage.MessageBuilder.build;
import static com.epam.queue.message.MessageType.A;
import static org.junit.Assert.assertEquals;

public class DefaultMessageReceiverTest {

    private final static String SPRITTING_REGEXP = " ";

    private static final String CORRECT_MESSAGE_A_TYPE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";

    private DefaultMessageReceiver messageReceiver;

    @Before
    public void setUp() throws Exception {
        InstructionMessageValidator instructionMessageValidator = new InstructionMessageValidator();

        messageReceiver = new DefaultMessageReceiver();
        messageReceiver.setValidator(instructionMessageValidator);
        messageReceiver.setQueue(new InstructionQueue());
    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException, ParseException {
        messageReceiver.receive(CORRECT_MESSAGE_A_TYPE);
    }

    @Test
    public void shouldAcceptWhenReceiveTwoEqualsCorrectMessages() throws ValidationException, ParseException {
        messageReceiver.receive(CORRECT_MESSAGE_A_TYPE);
        messageReceiver.receive(CORRECT_MESSAGE_A_TYPE);
    }

    @Test
    public void shouldCorrectlyBuildInstructionMessage() throws ParseException {
        InstructionMessage instructionMessage = build(CORRECT_MESSAGE_A_TYPE.split(SPRITTING_REGEXP));

        assertEquals(A, instructionMessage.getInstructionType());
        assertEquals("MZ89", instructionMessage.getProductCode());
        assertEquals(5678, instructionMessage.getQuantity());
        assertEquals(50, instructionMessage.getUom());
        assertEquals(new SimpleDateFormat(DATE_FORMAT).parse("2015-03-05T10:04:56.012Z"), instructionMessage.getTimestamp());
    }


    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenMessageIsNull() throws ValidationException, ParseException {
        messageReceiver.receive(null);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageIsEmptyString() throws ValidationException, ParseException {
        messageReceiver.receive("");
    }
}