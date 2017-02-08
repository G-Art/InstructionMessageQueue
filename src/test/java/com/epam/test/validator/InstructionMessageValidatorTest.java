package com.epam.test.validator;

import com.epam.data.InstructionMessage;
import com.epam.validator.ValidationException;
import com.epam.validator.Validator;
import com.epam.validator.impl.InstructionMessageValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.util.Date;

public class InstructionMessageValidatorTest {

    private Validator<InstructionMessage> underTest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws ParseException {
        underTest = new InstructionMessageValidator();
    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException {
        try {
            underTest.validate(createCorrectInstructionMessage());
        } catch (Throwable throwable) {
            Assert.fail(throwable.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectInstructionType() throws ValidationException {
        expect(ValidationException.class, "Error InstructionType field is empty or null");
        underTest.validate(createMessageWithIncorrectInstructionType());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() throws ValidationException {
        expect(ValidationException.class, "Error invalid ProductCode");
        underTest.validate(createMessageWithIncorrectProductCode());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() throws ValidationException {
        expect(ValidationException.class, "Error quantity must be positive");
        underTest.validate(createMessageWithIncorrectQuantity());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectUOM() throws ValidationException {
        expect(ValidationException.class, "Error uom must be positive and less then 256");
        underTest.validate(createMessageWithIncorrectUOM());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithNullTimestamp() throws ValidationException {
        expect(ValidationException.class, "Error date mustn't be null");
        underTest.validate(createMessageWithNullTimestamp());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectTimestamp() throws ValidationException {
        expect(ValidationException.class, "Error date is not valid");
        underTest.validate(createMessageWithIncorrectTimestamp());
    }


    private <T extends Throwable> void expect(Class<T> expectedThrowableClass, String expectedMessage) {
        thrown.expect(expectedThrowableClass);
        thrown.expectMessage(expectedMessage);
    }

    private InstructionMessage createCorrectInstructionMessage() {
        InstructionMessage message = new InstructionMessage();
        message.setInstructionType("A");
        message.setProductCode("AZ19");
        message.setUom(11);
        message.setQuantity(11);
        message.setTimestamp(new Date());
        return message;
    }

    private InstructionMessage createMessageWithIncorrectInstructionType() {
        InstructionMessage message = createCorrectInstructionMessage();
        message.setInstructionType("");
        return message;
    }

    private InstructionMessage createMessageWithIncorrectProductCode() {
        InstructionMessage message = createCorrectInstructionMessage();
        message.setProductCode("1111");
        return message;
    }


    private InstructionMessage createMessageWithIncorrectQuantity() {
        InstructionMessage message = createCorrectInstructionMessage();
        message.setQuantity(-5);
        return message;
    }

    private InstructionMessage createMessageWithIncorrectUOM() {
        InstructionMessage message = createCorrectInstructionMessage();
        message.setUom(1000);
        return message;
    }


    private InstructionMessage createMessageWithNullTimestamp() {
        InstructionMessage message = createCorrectInstructionMessage();
        message.setTimestamp(null);
        return message;
    }


    private InstructionMessage createMessageWithIncorrectTimestamp() {
        InstructionMessage message = createCorrectInstructionMessage();
        message.setTimestamp(new Date(-100));
        return message;
    }

}