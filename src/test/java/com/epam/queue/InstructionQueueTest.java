package com.epam.queue;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.message.MessageType;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static com.epam.queue.message.InstructionMessage.MessageBuilder.build;
import static org.junit.Assert.*;

public class InstructionQueueTest {

    private final static String SPRITTING_REGEXP = " ";

    private static final String CORRECT_MESSAGE_A_TYPE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String CORRECT_MESSAGE_D_TYPE = "InstructionMessage D MZ89 5678 50 2015-03-05T10:04:56.012Z";

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
        instructionQueue.enqueue(build(CORRECT_MESSAGE_A_TYPE.split(SPRITTING_REGEXP)));
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldChangeCountWhenMessagesEnqueuedAndDequeue() throws ParseException {
        int expected_count = 1;
        instructionQueue.enqueue(build(CORRECT_MESSAGE_A_TYPE.split(SPRITTING_REGEXP)));
        assertEquals(expected_count, instructionQueue.count());

        expected_count = 0;
        instructionQueue.dequeue();
        assertEquals(expected_count, instructionQueue.count());
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() throws ParseException {
        instructionQueue.enqueue(build(CORRECT_MESSAGE_D_TYPE.split(SPRITTING_REGEXP)));
        instructionQueue.enqueue(build(CORRECT_MESSAGE_A_TYPE.split(SPRITTING_REGEXP)));

        assertEquals(MessageType.A, instructionQueue.peek().getInstructionType());
    }

    @Test
    public void shouldPeekFirstAddedMessageWithTheSamePriority() throws ParseException {
        InstructionMessage firstAddedMessage = build(CORRECT_MESSAGE_A_TYPE.split(SPRITTING_REGEXP));
        instructionQueue.enqueue(firstAddedMessage);
        instructionQueue.enqueue(build(CORRECT_MESSAGE_A_TYPE.split(SPRITTING_REGEXP)));

        assertEquals(firstAddedMessage, instructionQueue.peek());
    }



}