package com.epam.queue;

import com.epam.queue.message.InstructionMessage;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static com.epam.queue.message.MessageType.*;
import static org.junit.Assert.*;

public class InstructionQueueTest {

    public static final String[] SPLITTED_CORRECT_MESSAGE_A_TYPE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z".split(" ");
    public static final String[] SPLITTED_CORRECT_MESSAGE_D_TYPE = "InstructionMessage D MZ89 5678 50 2015-03-05T10:04:56.012Z".split(" ");

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
        instructionQueue.enqueue(new InstructionMessage(SPLITTED_CORRECT_MESSAGE_A_TYPE));
        assertFalse(instructionQueue.isEmpty());
    }

    @Test
    public void shouldReturnCorrectCountWhenMessagesEnqueued() throws ParseException {
        int expected_count = 2;
        instructionQueue.enqueue(new InstructionMessage(SPLITTED_CORRECT_MESSAGE_A_TYPE));
        instructionQueue.enqueue(new InstructionMessage(SPLITTED_CORRECT_MESSAGE_D_TYPE));
        assertEquals(expected_count, instructionQueue.count());
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
        InstructionMessage messageAType = new InstructionMessage(SPLITTED_CORRECT_MESSAGE_A_TYPE);
        InstructionMessage messageDType = new InstructionMessage(SPLITTED_CORRECT_MESSAGE_D_TYPE);

        instructionQueue.enqueue(messageDType);
        instructionQueue.enqueue(messageAType);

        List<InstructionMessage> expectOrderedMessages = Arrays.asList(messageAType, messageDType);
        for (InstructionMessage expectOrderedMessage : expectOrderedMessages) {
            assertSame(expectOrderedMessage, instructionQueue.dequeue());
        }
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() throws ParseException {
        InstructionMessage expectMessage = new InstructionMessage(SPLITTED_CORRECT_MESSAGE_A_TYPE);

        instructionQueue.enqueue(new InstructionMessage(SPLITTED_CORRECT_MESSAGE_D_TYPE));
        instructionQueue.enqueue(expectMessage);

        assertEquals(expectMessage, instructionQueue.peek());
    }


}