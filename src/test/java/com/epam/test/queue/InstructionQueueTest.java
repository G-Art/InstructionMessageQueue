package com.epam.test.queue;

import com.epam.Constants;
import com.epam.data.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.queue.impl.DefaultInstructionQueue;
import com.epam.validation.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InstructionQueueTest {

    private InstructionQueue underTest;

    private InstructionMessage expectMessageAType;
    private InstructionMessage expectMessageAType1;
    private InstructionMessage expectMessageBType;
    private InstructionMessage expectMessageBType1;
    private InstructionMessage expectMessageCType;
    private InstructionMessage expectMessageDType;

    private int EXPECTED_COUNT;

    @Before
    public void setUp() throws ParseException, ValidationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

        underTest = new DefaultInstructionQueue();

        expectMessageAType = new InstructionMessage("A", "MZ89", 5678, 50,
                                                    dateFormat.parse("2015-03-05T10:04:51.012Z"));
        expectMessageAType1 = new InstructionMessage("A", "MZ86", 1111, 11,
                                                     dateFormat.parse("2015-03-05T10:04:54.012Z"));

        expectMessageBType = new InstructionMessage("B", "MZ88", 5677, 51,
                                                             dateFormat.parse("2015-03-05T10:04:52.012Z"));
        expectMessageBType1 = new InstructionMessage("B", "MZ90", 5677, 51,
                                                    dateFormat.parse("2015-03-05T10:04:52.012Z"));
        expectMessageCType = new InstructionMessage("C", "MZ87", 5676, 52,
                                                dateFormat.parse("2015-03-05T10:04:53.012Z"));
        expectMessageDType = new InstructionMessage("D", "MZ86", 5675, 53,
                                                             dateFormat.parse("2015-03-05T10:04:54.012Z"));

        underTest.enqueue(expectMessageBType);
        underTest.enqueue(expectMessageCType);
        underTest.enqueue(expectMessageAType1);
        underTest.enqueue(expectMessageDType);
        underTest.enqueue(expectMessageAType);
        underTest.enqueue(expectMessageBType1);
        underTest.enqueue(expectMessageDType);

        EXPECTED_COUNT = 7;

    }

    @Test
    public void shouldDequeueMessages() throws Exception {
        Assert.assertSame(expectMessageAType1, underTest.dequeue());
        Assert.assertSame(expectMessageAType, underTest.dequeue());
        Assert.assertSame(expectMessageBType, underTest.dequeue());
        Assert.assertSame(expectMessageBType1, underTest.dequeue());
        Assert.assertSame(expectMessageCType, underTest.dequeue());
        Assert.assertSame(expectMessageDType, underTest.dequeue());
        Assert.assertSame(expectMessageDType, underTest.dequeue());
    }

    @Test
    public void shouldPeekTheMostPriorityMessage() throws Exception {
        Assert.assertEquals(expectMessageAType1, underTest.peek());
        Assert.assertEquals(expectMessageAType1, underTest.peek());
    }

    @Test
    public void shouldReturnCorrectCount() throws Exception {
        Assert.assertEquals(EXPECTED_COUNT, underTest.count());
    }

    @Test
    public void shouldReturnZeroCount() throws Exception {
        cleanQueue();
        Assert.assertEquals(0, underTest.count());
    }

    @Test
    public void shouldReturnTrueIfQueueIsEmpty() throws Exception {
        cleanQueue();
        Assert.assertTrue(underTest.isEmpty());
    }

    @Test
    public void shouldReturnFalseIfQueueIsNotEmpty() throws Exception {
        Assert.assertFalse(underTest.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenEnqueueParameterIsNull() throws ValidationException {
        cleanQueue();
        underTest.enqueue(null);
    }


    private void cleanQueue(){
        underTest = new DefaultInstructionQueue();
    }
}