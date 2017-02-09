package com.epam.validator;

import com.epam.data.InstructionMessage;
import com.epam.queue.MessageType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.util.Date;

public class InstructionMessageValidatorTest {

    private InstructionMessageValidator messageValidator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws ParseException {
        messageValidator = new InstructionMessageValidator();
    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException {
        try {
            messageValidator.validate(createCorrectInstructionMessage());
        } catch (Throwable throwable) {
            Assert.fail(throwable.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectInstructionType() throws ValidationException {
        expect(ValidationException.class, "InstructionType field is empty or null");
        messageValidator.validate(createMessageWithIncorrectInstructionType());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() throws ValidationException {
        expect(ValidationException.class, "Invalid ProductCode");
        messageValidator.validate(createMessageWithIncorrectProductCode());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() throws ValidationException {
        expect(ValidationException.class, "Quantity must be positive");
        messageValidator.validate(createMessageWithIncorrectQuantity());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectUOM() throws ValidationException {
        expect(ValidationException.class, "UOM must be less then: 256. Actual UOM is:1000");
        messageValidator.validate(createMessageWithIncorrectUOM());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithNullTimestamp() throws ValidationException {
        expect(ValidationException.class, "Date mustn't be null");
        messageValidator.validate(createMessageWithNullTimestamp());
    }

    @Test
    public void shouldThrowExceptionWhenMessageWithIncorrectTimestamp() throws ValidationException {
        expect(ValidationException.class, "Date is not valid");
        messageValidator.validate(createMessageWithIncorrectTimestamp());
    }


    private <T extends Throwable> void expect(Class<T> expectedThrowableClass, String expectedMessage) {
        thrown.expect(expectedThrowableClass);
        thrown.expectMessage(expectedMessage);
    }

    private InstructionMessage createCorrectInstructionMessage() {
        InstructionMessage message = new InstructionMessage();
        message.setInstructionType(MessageType.A);
        message.setProductCode("AZ19");
        message.setUom(11);
        message.setQuantity(11);
        message.setTimestamp(new Date());
        return message;
    }

    private InstructionMessage createMessageWithIncorrectInstructionType() {
        InstructionMessage message = createCorrectInstructionMessage();
        message.setInstructionType(null);
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
        message.setTimestamp(new Date(System.currentTimeMillis()+1000));
        return message;
    }

}