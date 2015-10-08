package com.epam.test.receiver;

import com.epam.receiver.MessageReceiver;
import com.epam.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class DefaultMessageReceiverTest {

    @Resource(name = "messageReceiver")
    MessageReceiver messageReceiver;

    @Test
    public void shouldNotBeException() throws ValidationException {
        messageReceiver.receive("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBeExceptionIfMessageIsWrong() throws ValidationException {
        messageReceiver.receive("InstructionA MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldBeExceptionIfMessageIsInvalid() throws ValidationException {
        messageReceiver.receive("InstructionMessage XM MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBeExceptionIfMessageIsNull() throws ValidationException {
        messageReceiver.receive(null);
    }
}