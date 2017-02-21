package com.epam.queue;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static com.epam.queue.MessageType.*;
import static org.junit.Assert.*;

public class InstructionQueueTest {

    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private InstructionQueue instructionQueue;

    @Before
    public void setUp() {
        instructionQueue = new InstructionQueue();
    }

    @Test
    public void shouldReturnTrueWhenQueueIsEmpty() {
        assertTrue(instructionQueue.isEmpty());
    }

    @Test
    public void shouldReturnZeroCountWhenNoOneMessagesEnqueued() {
        assertEquals(0, instructionQueue.count());
    }

    @Test
    public void shouldReturnFalseWhenQueueIsNotEmpty() throws ParseException {
        instructionQueue.enqueue(createInstructionMessage(A, "MZ89", 5678, 50, "2015-03-05T10:04:51.012Z"));
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldReturnCorrectCountWhenMessagesEnqueued() throws ParseException {
        instructionQueue.enqueue(createInstructionMessage(A, "MZ89", 5678, 50, "2015-03-05T10:04:51.012Z"));
        instructionQueue.enqueue(createInstructionMessage(B, "MZ89", 5678, 50, "2015-03-05T10:04:51.012Z"));
        assertEquals(2, instructionQueue.count());
    }

    @Test()
    public void shouldEnqueueWhenEnqueueParameterIsNull() {
        instructionQueue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenWhenDequeueEmptyQueue() {
        instructionQueue.dequeue();
    }

    @Test()
    public void shouldReturnNullWhenPeekEmptyQueue() {
        assertNull(instructionQueue.peek());
    }

    @Test
    public void shouldReturnPrioritizedCorrectOrderedMessages() throws ParseException {
        InstructionMessage messageAType = createInstructionMessage(A, "MZ86", 1111, 11, "2015-03-05T10:04:54.012Z");
        InstructionMessage messageBType = createInstructionMessage(B, "MZ88", 5677, 51, "2015-03-05T10:04:52.012Z");
        InstructionMessage messageBType2 = createInstructionMessage(B, "MZ90", 5677, 51, "2015-03-05T10:04:52.012Z");
        InstructionMessage messageCType = createInstructionMessage(C, "MZ87", 5676, 52, "2015-03-05T10:04:53.012Z");
        InstructionMessage messageDType = createInstructionMessage(D, "MZ86", 5675, 53, "2015-03-05T10:04:54.012Z");

        instructionQueue.enqueue(messageBType);
        instructionQueue.enqueue(messageCType);
        instructionQueue.enqueue(messageDType);
        instructionQueue.enqueue(messageAType);
        instructionQueue.enqueue(messageBType2);
        instructionQueue.enqueue(messageDType);

        List<InstructionMessage> expectOrderedMessages = Arrays.asList(messageAType, messageBType, messageBType2, messageCType, messageDType, messageDType);
        for (InstructionMessage expectOrderedMessage : expectOrderedMessages) {
            assertSame(expectOrderedMessage, instructionQueue.dequeue());
        }
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() throws ParseException {
        InstructionMessage expectMessage = createInstructionMessage(A, "MZ89", 5678, 50, "2015-03-05T10:04:51.012Z");

        instructionQueue.enqueue(createInstructionMessage(B, "MZ88", 5677, 51, "2015-03-05T10:04:52.012Z"));
        instructionQueue.enqueue(createInstructionMessage(B, "MZ90", 5677, 51, "2015-03-05T10:04:52.012Z"));
        instructionQueue.enqueue(createInstructionMessage(C, "MZ87", 5676, 52, "2015-03-05T10:04:53.012Z"));
        instructionQueue.enqueue(createInstructionMessage(D, "MZ86", 5675, 53, "2015-03-05T10:04:54.012Z"));
        instructionQueue.enqueue(expectMessage);

        assertEquals(expectMessage, instructionQueue.peek());
    }


    private InstructionMessage createInstructionMessage(MessageType instructionType, String productCode, int quantity,
                                                        int uom, String date) throws ParseException {
        InstructionMessage message = new InstructionMessage();
        message.setInstructionType(instructionType);
        message.setProductCode(productCode);
        message.setUom(uom);
        message.setQuantity(quantity);
        message.setTimestamp(new SimpleDateFormat(DATE_FORMAT).parse(date));
        return message;
    }
}