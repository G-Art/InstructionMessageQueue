package com.epam.queue;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.message.MessageType;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static com.epam.queue.message.MessageType.A;
import static com.epam.queue.message.MessageType.C;
import static com.epam.queue.message.MessageType.D;
import static org.junit.Assert.*;

public class InstructionQueueTest {

    private static final MessageType aMessageType = A;
    private static final MessageType cMessageType = C;
    private static final MessageType dMessageType = D;
    private static final String productCode = "MZ89";
    private static final Integer quantity = 99;
    private static final Integer uom = 50;
    private static final Date timestamp = new Date(999999L);

    private static final int EXPECTED_COUNT_FOR_EMPTY_QUEUE = 0;

    private static final InstructionMessage CORRECT_MESSAGE_A_TYPE = new InstructionMessage(aMessageType, productCode, quantity, uom, timestamp);
    private static final InstructionMessage CORRECT_MESSAGE_D_TYPE = new InstructionMessage(dMessageType, productCode, quantity, uom, timestamp);

    private InstructionQueue instructionQueue = new InstructionQueue();

    @Test
    public void shouldBeEmpty() {
        assertTrue(instructionQueue.isEmpty());
    }

    @Test
    public void shouldBeNotEmpty() {
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldNotContainMessagesWhenNoOneMessagesEnqueued() {
        assertEquals(EXPECTED_COUNT_FOR_EMPTY_QUEUE, instructionQueue.count());
    }

    @Test
    public void shouldReturnCorrectCountWhenMessagesEnqueued() {
        int expected_count = 1;
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);
        assertEquals(expected_count, instructionQueue.count());

    }

    @Test
    public void shouldReturnCorrectCountWhenMessagesDequeue() {
        int expected_count = 0;
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);
        instructionQueue.dequeue();
        assertEquals(expected_count, instructionQueue.count());
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() {
        instructionQueue.enqueue(CORRECT_MESSAGE_D_TYPE);
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);

        assertEquals(A, instructionQueue.peek().getInstructionType());
    }

    @Test
    public void shouldPeekFirstAddedMessageWithTheSamePriority() {
        InstructionMessage firstAddedMessage = new InstructionMessage(aMessageType, productCode, quantity, uom, timestamp);
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(CORRECT_MESSAGE_A_TYPE);

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }
    @Test
    public void shouldPeekFirstAddedMessageWithTheSamePriorityButDifferentMessageType() {
        InstructionMessage firstAddedMessage = new InstructionMessage(dMessageType, productCode, quantity, uom, timestamp);
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(new InstructionMessage(cMessageType, productCode, quantity, uom, timestamp));

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }

}