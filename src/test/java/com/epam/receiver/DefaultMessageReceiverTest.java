package com.epam.receiver;

import com.epam.parser.InstructionMessageParser;
import com.epam.parser.MessageParseException;
import com.epam.queue.InstructionQueue;
import com.epam.receiver.impl.DefaultMessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.MessageValidationException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class DefaultMessageReceiverTest {

    private static final String CORRECT_MESSAGE_A_TYPE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z\n";

    private static final String MESSAGE_WITH_INVALID_PRODUCT_CODE = "InstructionMessage A TEST 5678 50 2015-03-05T10:04:56.012Z\n";

    private InstructionQueue instructionQueue = new InstructionQueue();
    private DefaultMessageReceiver messageReceiver = new DefaultMessageReceiver(new InstructionMessageValidator(), instructionQueue, new InstructionMessageParser());

    @Test
    public void shouldPutMessageIntoQueue() {
        messageReceiver.receive(CORRECT_MESSAGE_A_TYPE);
        assertFalse(instructionQueue.isEmpty());
    }

    @Test(expected = MessageParseException.class)
    public void shouldThrowParsingExceptionWhenMessageIsNull() {
        messageReceiver.receive(null);
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithInvalidProductCode() {
        messageReceiver.receive(MESSAGE_WITH_INVALID_PRODUCT_CODE);
    }
}