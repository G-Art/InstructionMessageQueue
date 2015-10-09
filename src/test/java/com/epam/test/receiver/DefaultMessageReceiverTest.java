package com.epam.test.receiver;

import com.epam.receiver.MessageReceiver;
import com.epam.validation.ValidationException;
import org.junit.Assert;
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

    private static final String CORRECT_MESSAGE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_INCORRECT_PREFIX = "InstructionA MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_INCORRECT_INSTRUCTION_CODE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException {
        try {
            messageReceiver.receive(CORRECT_MESSAGE);
        } catch (Throwable throwable) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectPrefix() throws ValidationException {
        messageReceiver.receive(MESSAGE_WITH_INCORRECT_PREFIX);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectInstructionCode() throws ValidationException {
        messageReceiver.receive(MESSAGE_WITH_INCORRECT_INSTRUCTION_CODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenMessageIsNull() throws ValidationException {
        messageReceiver.receive(null);
    }
}