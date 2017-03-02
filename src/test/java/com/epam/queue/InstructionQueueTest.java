package com.epam.queue;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.message.MessageType;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static com.epam.queue.message.MessageType.A;
import static com.epam.queue.message.MessageType.D;
import static org.junit.Assert.*;

public class InstructionQueueTest {

    private static final MessageType aMessageType = A;
    private static final MessageType dMessageType = D;
    private static final String productCode = "MZ89";
    private static final Integer quantity = 99;
    private static final Integer uom = 50;
    private static final Date timestamp = new Date(999999L);

    private static final InstructionMessage CORRECT_MESSAGE_A_TYPE = buildMessage(aMessageType, productCode, quantity, uom, timestamp);
    private static final InstructionMessage CORRECT_MESSAGE_D_TYPE = buildMessage(dMessageType, productCode, quantity, uom, timestamp);

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
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldChangeCountWhenMessagesEnqueuedAndDequeue() throws ParseException {
        int expected_count = 1;
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);
        assertEquals(expected_count, instructionQueue.count());

        expected_count = 0;
        instructionQueue.dequeue();
        assertEquals(expected_count, instructionQueue.count());
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() throws ParseException {
        instructionQueue.enqueue(CORRECT_MESSAGE_D_TYPE);
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);

        assertEquals(A, instructionQueue.peek().getInstructionType());
    }

    @Test
    public void shouldPeekFirstAddedMessageWithTheSamePriority() throws ParseException {
        InstructionMessage firstAddedMessage = buildMessage(aMessageType, productCode, quantity, uom, timestamp);
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }


    private static InstructionMessage buildMessage(MessageType messageType, String productCode, Integer quantity, Integer uom, Date timestamp) {
        return new InstructionMessage(messageType, productCode, quantity, uom, timestamp);
    }

}