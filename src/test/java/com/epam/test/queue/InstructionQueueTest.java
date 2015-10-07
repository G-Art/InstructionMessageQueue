package com.epam.test.queue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.data.InstructionMessage;
import com.epam.queue.InstructionQueue;
import com.epam.queue.impl.InstructionQueueImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class InstructionQueueTest {

    private InstructionQueue underTest;
    private InstructionMessage expectMessage;

    @Before
    public void setUp() throws ParseException {
        underTest = new InstructionQueueImpl();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        InstructionMessage message = new InstructionMessage("A", "MZ89", 5678, 50, dateFormat.parse("2015-03-05T10:04:51.012Z"));
        InstructionMessage message1 = new InstructionMessage("B", "MZ88", 5677, 51, dateFormat.parse("2015-03-05T10:04:52.012Z"));
        InstructionMessage message2 = new InstructionMessage("C", "MZ87", 5676, 52, dateFormat.parse("2015-03-05T10:04:53.012Z"));
        InstructionMessage message3 = new InstructionMessage("D", "MZ86", 5675, 53, dateFormat.parse("2015-03-05T10:04:54.012Z"));

        underTest.enqueue(message1);
        underTest.enqueue(message2);
        underTest.enqueue(message3);
        underTest.enqueue(message);

        expectMessage = message;
    }

    @Test
    public void shouldReturnAndRemoveMessages() throws Exception {
        Assert.assertSame(expectMessage, underTest.dequeue());
        Assert.assertNotSame(expectMessage, underTest.dequeue());
    }

    @Test
    public void shouldPeekTheMostPriorityMessage() throws Exception {
        Assert.assertEquals(expectMessage, underTest.peek());
        Assert.assertEquals(expectMessage, underTest.peek());
        Assert.assertEquals(expectMessage, underTest.peek());
    }

    @Test
    public void shouldBeCorrectCount() throws Exception {
        Assert.assertEquals(4, underTest.count());

        InstructionQueue emptyQueue = new InstructionQueueImpl();
        Assert.assertEquals(0, emptyQueue.count());
    }

    @Test
    public void shouldBeEmpty() throws Exception {
        InstructionQueue emptyQueue = new InstructionQueueImpl();
        Assert.assertTrue(emptyQueue.isEmpty());
    }

    @Test
    public void shouldBeNotEmpty() throws Exception {
        Assert.assertFalse(underTest.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldBeExceptionIfEnqueueParameterIsNull(){
        InstructionQueue emptyQueue = new InstructionQueueImpl();
        emptyQueue.enqueue(null);
    }
}