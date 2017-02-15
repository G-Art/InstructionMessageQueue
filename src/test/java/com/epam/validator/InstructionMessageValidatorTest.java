package com.epam.validator;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

public class InstructionMessageValidatorTest {

    private InstructionMessageValidator messageValidator;

    @Before
    public void setUp() throws ParseException {
        messageValidator = new InstructionMessageValidator();
    }

    @Test
    public void shouldValidateWhenMessageIsCorrect() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutInstructionType() throws ValidationException {
        messageValidator.validate("InstructionMessage MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectInstructionType() throws ValidationException {
        messageValidator.validate("InstructionMessage Q MZ89 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutProductCode() throws ValidationException {
        messageValidator.validate("InstructionMessage A 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() throws ValidationException {
        messageValidator.validate("InstructionMessage A 11111111 5678 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutQuantity() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 -5 50 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutIncorrectUOM() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 5678 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectUOM() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 5678 1000 2015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutTimestamp() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 5678 50 ");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithTimestampInFuture() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 5678 50 3015-03-05T10:04:56.012Z");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithTimestampInPast() throws ValidationException {
        messageValidator.validate("InstructionMessage A MZ89 5678 50 1015-03-05T10:04:56.012Z");
    }

}