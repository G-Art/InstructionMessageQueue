package com.epam.test.queue;

import com.epam.data.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.queue.impl.DefaultInstructionQueue;
import com.epam.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class InstructionQueueTest{

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private InstructionQueue underTest;

    private InstructionMessage expectMessageAType;

    List<InstructionMessage> expectOrderedMessages;


    @Before
    public void setUp() throws ParseException, ValidationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        expectOrderedMessages = new ArrayList<InstructionMessage>();

        underTest = new DefaultInstructionQueue();

        expectMessageAType = createInstructionMessage("A", "MZ89", 5678, 50,
                                                      dateFormat.parse("2015-03-05T10:04:51.012Z"));
        InstructionMessage expectMessageAType1 = createInstructionMessage("A", "MZ86", 1111, 11,
                                                                          dateFormat.parse("2015-03-05T10:04:54.012Z"));

        InstructionMessage expectMessageBType = createInstructionMessage("B", "MZ88", 5677, 51,
                                                                         dateFormat.parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage expectMessageBType1 = createInstructionMessage("B", "MZ90", 5677, 51,
                                                                          dateFormat.parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage expectMessageCType = createInstructionMessage("C", "MZ87", 5676, 52,
                                                                         dateFormat.parse("2015-03-05T10:04:53.012Z"));
        InstructionMessage expectMessageDType = createInstructionMessage("D", "MZ86", 5675, 53,
                                                                         dateFormat.parse("2015-03-05T10:04:54.012Z"));



        underTest.enqueue(expectMessageBType1);         expectOrderedMessages.add(expectMessageAType);
        underTest.enqueue(expectMessageCType);          expectOrderedMessages.add(expectMessageAType1);
        underTest.enqueue(expectMessageAType);          expectOrderedMessages.add(expectMessageBType1);
        underTest.enqueue(expectMessageDType);          expectOrderedMessages.add(expectMessageBType);
        underTest.enqueue(expectMessageAType1);         expectOrderedMessages.add(expectMessageCType);
        underTest.enqueue(expectMessageBType);          expectOrderedMessages.add(expectMessageDType);
        underTest.enqueue(expectMessageDType);          expectOrderedMessages.add(expectMessageDType);

    }

    @Test
    public void shouldReturnCorrectOrderedMessages() {
        for (InstructionMessage expectOrderedMessage : expectOrderedMessages) {
            assertSame(expectOrderedMessage, underTest.dequeue());
        }
    }

    @Test
    public void shouldPeekMessageWithHighestPriority() {
        assertEquals(expectMessageAType, underTest.peek());
    }

    @Test
    public void shouldReturnCorrectCount(){
        assertEquals(expectOrderedMessages.size(), underTest.count());
    }

    @Test
    public void shouldReturnZeroCount() {
        cleanQueue();
        assertEquals(0, underTest.count());
    }

    @Test
    public void shouldReturnTrueIfQueueIsEmpty() {
        cleanQueue();
        assertTrue(underTest.isEmpty());
    }

    @Test
    public void shouldReturnFalseIfQueueIsNotEmpty() {
        assertFalse(underTest.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenEnqueueParameterIsNull() {
        cleanQueue();
        underTest.enqueue(null);
    }


    private void cleanQueue() {
        underTest = new DefaultInstructionQueue();
    }

    private InstructionMessage createInstructionMessage(String instructionType, String productCode, int quantity,
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