package com.epam.receiver;

import com.epam.parsers.InstructionMessageParser;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.impl.DefaultMessageReceiver;
import com.epam.validator.ValidationException;
import com.epam.validator.InstructionMessageValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultMessageReceiverTest {

    private MessageReceiver messageReceiver;

    private static final String CORRECT_MESSAGE                         = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_INCORRECT_PREFIX           = "InstructionA MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_INCORRECT_INSTRUCTION_CODE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";

    @Before
    public void setUp() throws Exception {
        messageReceiver = new DefaultMessageReceiver(new InstructionMessageParser(), new InstructionMessageValidator(), new InstructionQueue());
    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException {
        try {
            messageReceiver.receive(CORRECT_MESSAGE);
        } catch (Throwable throwable) {
            Assert.fail(throwable.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectPrefix() throws ValidationException {
        messageReceiver.receive(MESSAGE_WITH_INCORRECT_PREFIX);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectInstructionCode() throws ValidationException {
        messageReceiver.receive(MESSAGE_WITH_INCORRECT_INSTRUCTION_CODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenMessageIsNull() throws ValidationException {
        messageReceiver.receive(null);
    }
}