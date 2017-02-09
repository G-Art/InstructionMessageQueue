package com.epam.queue;

import com.epam.data.InstructionMessage;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class InstructionQueueTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private InstructionQueue instructionQueue;

    private InstructionMessage expectMessageAType;


    @Before
    public void setUp() {
        instructionQueue = new InstructionQueue();
    }

    @Test
    public void shouldReturnCorrectOrderedMessages() throws ParseException {
        List<InstructionMessage> expectOrderedMessages = defineQueue();
        for (InstructionMessage expectOrderedMessage : expectOrderedMessages) {
            assertSame(expectOrderedMessage, instructionQueue.dequeue());
        }
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() throws ParseException {
        defineQueue();
        assertEquals(expectMessageAType, instructionQueue.peek());
    }

    @Test
    public void shouldReturnCorrectCount() throws ParseException {
        defineQueue();
        assertEquals(7, instructionQueue.count());
    }

    @Test
    public void shouldReturnZeroCount() {
        assertEquals(0, instructionQueue.count());
    }

    @Test
    public void shouldReturnTrueIfQueueIsEmpty() {
        assertTrue(instructionQueue.isEmpty());
    }

    @Test
    public void shouldReturnFalseIfQueueIsNotEmpty() throws ParseException {
        defineQueue();
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

    private List<InstructionMessage> defineQueue() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        expectMessageAType = createInstructionMessage("A", "MZ89", 5678, 50,
                dateFormat.parse("2015-03-05T10:04:51.012Z"));
        InstructionMessage messageAType1 = createInstructionMessage("A", "MZ86", 1111, 11,
                dateFormat.parse("2015-03-05T10:04:54.012Z"));

        InstructionMessage messageBType = createInstructionMessage("B", "MZ88", 5677, 51,
                dateFormat.parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage messageBType1 = createInstructionMessage("B", "MZ90", 5677, 51,
                dateFormat.parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage messageCType = createInstructionMessage("C", "MZ87", 5676, 52,
                dateFormat.parse("2015-03-05T10:04:53.012Z"));
        InstructionMessage messageDType = createInstructionMessage("D", "MZ86", 5675, 53,
                dateFormat.parse("2015-03-05T10:04:54.012Z"));


        instructionQueue.enqueue(messageBType1);
        instructionQueue.enqueue(messageCType);
        instructionQueue.enqueue(expectMessageAType);
        instructionQueue.enqueue(messageDType);
        instructionQueue.enqueue(messageAType1);
        instructionQueue.enqueue(messageBType);
        instructionQueue.enqueue(messageDType);

        return Arrays.asList(expectMessageAType, messageAType1, messageBType1, messageBType, messageCType, messageDType, messageDType);
    }

    private InstructionMessage createInstructionMessage(String instructionType, String productCode, int quantity,
                                                        int uom, Date date) {
        InstructionMessage message = new InstructionMessage();
        message.setInstructionType(MessageType.valueOf(instructionType));
        message.setProductCode(productCode);
        message.setUom(uom);
        message.setQuantity(quantity);
        message.setTimestamp(date);
        return message;
    }
}