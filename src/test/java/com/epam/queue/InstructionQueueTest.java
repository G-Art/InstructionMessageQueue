package com.epam.queue;

import com.epam.data.InstructionMessage;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static com.epam.queue.MessageType.*;
import static com.epam.utils.DateTimeUtils.parse;
import static org.junit.Assert.*;

public class InstructionQueueTest {

    private InstructionQueue instructionQueue;

    @Before
    public void setUp() {
        instructionQueue = new InstructionQueue();
    }

    @Test
    public void shouldReturnCorrectOrderedMessages() throws ParseException {
        List<InstructionMessage> expectOrderedMessages = enqueue6MessagesToQueue();
        for (InstructionMessage expectOrderedMessage : expectOrderedMessages) {
            assertSame(expectOrderedMessage, instructionQueue.dequeue());
        }
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() throws ParseException {
        InstructionMessage expectMessage = createInstructionMessage(A, "MZ89", 5678, 50, parse("2015-03-05T10:04:51.012Z"));
        instructionQueue.enqueue(expectMessage);
        enqueue6MessagesToQueue();
        assertEquals(expectMessage, instructionQueue.peek());
    }

    @Test
    public void shouldReturnCorrectCountIfMessagesEnqueued() throws ParseException {
        enqueue6MessagesToQueue();
        assertEquals(6, instructionQueue.count());
    }

    @Test
    public void shouldReturnZeroCountIfNoOneMessagesEnqueued() {
        assertEquals(0, instructionQueue.count());
    }

    @Test
    public void shouldReturnTrueIfQueueIsEmpty() {
        assertTrue(instructionQueue.isEmpty());
    }

    @Test
    public void shouldReturnFalseIfQueueIsNotEmpty() throws ParseException {
        enqueue6MessagesToQueue();
        assertFalse(instructionQueue.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenEnqueueParameterIsNull() {
        instructionQueue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenIfDequeueEmptyQueue() {
        instructionQueue.dequeue();
    }

    private List<InstructionMessage> enqueue6MessagesToQueue() throws ParseException {

        InstructionMessage messageAType = createInstructionMessage(A, "MZ86", 1111, 11,
                parse("2015-03-05T10:04:54.012Z"));
        InstructionMessage messageBType = createInstructionMessage(B, "MZ88", 5677, 51,
                parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage messageBType2 = createInstructionMessage(B, "MZ90", 5677, 51,
                parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage messageCType = createInstructionMessage(C, "MZ87", 5676, 52,
                parse("2015-03-05T10:04:53.012Z"));
        InstructionMessage messageDType = createInstructionMessage(D, "MZ86", 5675, 53,
                parse("2015-03-05T10:04:54.012Z"));


        instructionQueue.enqueue(messageBType);
        instructionQueue.enqueue(messageCType);
        instructionQueue.enqueue(messageDType);
        instructionQueue.enqueue(messageAType);
        instructionQueue.enqueue(messageBType2);
        instructionQueue.enqueue(messageDType);

        return Arrays.asList(messageAType, messageBType, messageBType2, messageCType, messageDType, messageDType);
    }

    private InstructionMessage createInstructionMessage(MessageType instructionType, String productCode, int quantity,
                                                        int uom, Date date) {
        InstructionMessage message = new InstructionMessage();
        message.setInstructionType(instructionType);
        message.setProductCode(productCode);
        message.setUom(uom);
        message.setQuantity(quantity);
        message.setTimestamp(date);
        return message;
    }
}