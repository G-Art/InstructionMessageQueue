package com.epam.queue;

import com.epam.queue.message.InstructionMessage;
import org.junit.Test;

import java.util.Date;

import static com.epam.queue.message.MessageType.*;
import static org.junit.Assert.*;

public class InstructionQueueTest {

    private static final String VALID_PRODUCT_CODE = "MZ89";
    private static final Integer VALID_QUANTITY = 99;
    private static final Integer VALID_UOM = 50;
    private static final Date VALID_TIMESTAMP = new Date(999999L);

    private static final int EXPECTED_COUNT_FOR_EMPTY_QUEUE = 0;
    private static final int EXPECTED_COUNT_FOR_NOT_EMPTY_QUEUE = 1;

    private static final InstructionMessage MESSAGE_A_TYPE = new InstructionMessage(A, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP);
    private static final InstructionMessage MESSAGE_D_TYPE = new InstructionMessage(D, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP);

    private InstructionQueue instructionQueue = new InstructionQueue();

    @Test
    public void shouldBeEmpty() {
        assertTrue(instructionQueue.isEmpty());
    }

    @Test
    public void shouldBeNotEmpty() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldNotContainMessagesWhenNoMessagesEnqueued() {
        assertEquals(EXPECTED_COUNT_FOR_EMPTY_QUEUE, instructionQueue.count());
    }

    @Test
    public void shouldReturnCorrectCountWhenMessagesEnqueued() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        assertEquals(EXPECTED_COUNT_FOR_NOT_EMPTY_QUEUE, instructionQueue.count());
    }

    @Test
    public void shouldBeNotEmptyWhenMessagesEnqueued() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldReturnNullWhenDequeueEmptyQueue() {
        assertNull(instructionQueue.dequeue());
    }

    @Test
    public void shouldReturnCorrectCountWhenMessagesDequeue() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        instructionQueue.dequeue();
        assertEquals(EXPECTED_COUNT_FOR_EMPTY_QUEUE, instructionQueue.count());
    }

    @Test
    public void shouldBeEmptyWhenLastMessagesDequeue() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        instructionQueue.dequeue();
        assertTrue(instructionQueue.isEmpty());
    }

    @Test
    public void shouldDequeueMessageWithHighestPriority() {
        instructionQueue.enqueue(MESSAGE_D_TYPE);
        instructionQueue.enqueue(MESSAGE_A_TYPE);

        assertEquals(MESSAGE_A_TYPE, instructionQueue.dequeue());
    }

    @Test
    public void shouldDequeueMessageWithHighestPriorityWhenHighestPriorityMessageEnqueuedFirst() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        instructionQueue.enqueue(MESSAGE_D_TYPE);

        assertEquals(MESSAGE_A_TYPE, instructionQueue.dequeue());
    }

    @Test
    public void shouldDequeueFirstAddedMessageWithTheSamePriority() {
        InstructionMessage firstAddedMessage = new InstructionMessage(A, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP);
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(MESSAGE_A_TYPE);

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }

    @Test
    public void shouldDequeueFirstAddedMessageWithTheSamePriorityButDifferentMessageType() {
        InstructionMessage firstAddedMessage = new InstructionMessage(D, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP);
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(new InstructionMessage(C, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP));

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }

    @Test
    public void shouldReturnNullWhenPeekEmptyQueue() {
        assertNull(instructionQueue.peek());
    }

    @Test
    public void shouldNotBeEmptyWhenPeekNotEmptyQueue() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        instructionQueue.peek();
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldReturnCorrectCountWhenPeekNotEmptyQueue() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        instructionQueue.peek();
        assertEquals(EXPECTED_COUNT_FOR_NOT_EMPTY_QUEUE, instructionQueue.count());
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() {
        instructionQueue.enqueue(MESSAGE_D_TYPE);
        instructionQueue.enqueue(MESSAGE_A_TYPE);

        assertEquals(MESSAGE_A_TYPE, instructionQueue.peek());
    }

    @Test
    public void shouldPeekMessageWithHighestPriorityWhenHighestPriorityMessageEnqueuedFirst() {
        instructionQueue.enqueue(MESSAGE_A_TYPE);
        instructionQueue.enqueue(MESSAGE_D_TYPE);

        assertEquals(MESSAGE_A_TYPE, instructionQueue.peek());
    }

    @Test
    public void shouldPeekFirstAddedMessageWithTheSamePriority() {
        InstructionMessage firstAddedMessage = new InstructionMessage(A, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP);
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(MESSAGE_A_TYPE);

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }

    @Test
    public void shouldPeekFirstAddedMessageWithTheSamePriorityButDifferentMessageType() {
        InstructionMessage firstAddedMessage = new InstructionMessage(D, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP);
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(new InstructionMessage(C, VALID_PRODUCT_CODE, VALID_QUANTITY, VALID_UOM, VALID_TIMESTAMP));

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }

}