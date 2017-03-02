package com.epam.receiver;

import com.epam.parser.InstructionMessageParseException;
import com.epam.parser.InstructionMessageParser;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.impl.DefaultMessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

public class DefaultMessageReceiverTest {

    private static final String CORRECT_MESSAGE_A_TYPE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z\n";


    private DefaultMessageReceiver messageReceiver;

    @Before
    public void setUp() throws Exception {
        messageReceiver = new DefaultMessageReceiver(new InstructionMessageValidator(), new InstructionQueue(), new InstructionMessageParser());
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

    @Test(expected = InstructionMessageParseException.class)
    public void shouldThrowExceptionWhenMessageIsNull() throws ValidationException, ParseException {
        messageReceiver.receive(null);
    }


    @Test(expected = InstructionMessageParseException.class)
    public void shouldThrowExceptionWhenMessageIsEmptyString() throws ValidationException, ParseException {
        messageReceiver.receive("");
    }
}