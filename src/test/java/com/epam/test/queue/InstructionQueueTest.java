package com.epam.test.queue;

import com.epam.data.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.queue.impl.DefaultInstructionQueue;
import com.epam.validation.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class InstructionQueueTest {

    private InstructionQueue underTest;

    @Resource(name = "instructionsPriority")
    private Map<String, Integer> instructionsPriority;

    @Resource(name = "dateFormat")
    private String dateFormatPattern;

    private InstructionMessage expectMessage;
    private InstructionMessage expectMessage2;

    @Before
    public void setUp() throws ParseException, ValidationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

        underTest = new DefaultInstructionQueue(instructionsPriority);

        expectMessage = new InstructionMessage("A", "MZ89", 5678, 50,
                                               dateFormat.parse("2015-03-05T10:04:51.012Z"));
        expectMessage2 = new InstructionMessage("B", "MZ88", 5677, 51,
                                                dateFormat.parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage message2 = new InstructionMessage("C", "MZ87", 5676, 52,
                                                             dateFormat.parse("2015-03-05T10:04:53.012Z"));
        InstructionMessage message3 = new InstructionMessage("D", "MZ86", 5675, 53,
                                                             dateFormat.parse("2015-03-05T10:04:54.012Z"));

        underTest.enqueue(expectMessage);
        underTest.enqueue(message2);
        underTest.enqueue(expectMessage2);
        underTest.enqueue(message3);
        underTest.enqueue(expectMessage);

    }

    @Test
    public void shouldReturnAndRemoveMessages() throws Exception {
        Assert.assertSame(expectMessage, underTest.dequeue());
        Assert.assertSame(expectMessage, underTest.dequeue());
        Assert.assertSame(expectMessage2, underTest.dequeue());
    }

    @Test
    public void shouldPeekTheMostPriorityMessage() throws Exception {
        Assert.assertEquals(expectMessage, underTest.peek());
        Assert.assertEquals(expectMessage, underTest.peek());
        Assert.assertEquals(expectMessage, underTest.peek());
    }

    @Test
    public void shouldBeCorrectCount() throws Exception {
        Assert.assertEquals(5, underTest.count());

        InstructionQueue emptyQueue = new DefaultInstructionQueue(instructionsPriority);
        Assert.assertEquals(0, emptyQueue.count());
    }

    @Test
    public void shouldBeEmpty() throws Exception {
        InstructionQueue emptyQueue = new DefaultInstructionQueue(instructionsPriority);
        Assert.assertTrue(emptyQueue.isEmpty());
    }

    @Test
    public void shouldBeNotEmpty() throws Exception {
        Assert.assertFalse(underTest.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldBeExceptionIfEnqueueParameterIsNull() throws ValidationException {
        InstructionQueue emptyQueue = new DefaultInstructionQueue(instructionsPriority);
        emptyQueue.enqueue(null);
    }
}