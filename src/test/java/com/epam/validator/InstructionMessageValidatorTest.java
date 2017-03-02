package com.epam.validator;

import com.epam.queue.message.InstructionMessage;
import com.epam.queue.message.MessageType;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class InstructionMessageValidatorTest {

    private static final MessageType aMessageType = MessageType.A;
    private static final String productCode = "MZ89";
    private static final Integer quantity = 99;
    private static final Integer uom = 50;
    private static final Date timestamp = new Date(999999L);

    private InstructionMessageValidator messageValidator;

    @Before
    public void setUp() throws ParseException {
        messageValidator = new InstructionMessageValidator();
    }

    @Test
    public void shouldNotThrowExceptionWhenMessageIsCorrect() throws ValidationException {
        messageValidator.validate(buildMessage(aMessageType, productCode, quantity, uom, timestamp));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() {
        String wrongProductCode = "zm_89";
        messageValidator.validate(buildMessage(aMessageType, wrongProductCode, quantity, uom, timestamp));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() {
        Integer wrongQuantity = -1;
        messageValidator.validate(buildMessage(aMessageType, productCode, wrongQuantity, uom, timestamp));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUOMOverMaxValue() {
        Integer wrongUom = 256;
        messageValidator.validate(buildMessage(aMessageType, productCode, quantity, wrongUom, timestamp));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUOMLassMinValue() {
        Integer wrongUom = -1;
        messageValidator.validate(buildMessage(aMessageType, productCode, quantity, wrongUom, timestamp));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithTimestampBeforeUnixEpoch() {
        Date unixEpoch = new Date(0);
        messageValidator.validate(buildMessage(aMessageType, productCode, quantity, uom, unixEpoch));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithTimestampInFuture() {
        Date dateInFuture = new Date(System.currentTimeMillis()+999999);
        messageValidator.validate(buildMessage(aMessageType, productCode, quantity, uom, dateInFuture));
    }

    private static InstructionMessage buildMessage(MessageType messageType, String productCode, Integer quantity, Integer uom, Date timestamp) {
        return new InstructionMessage(messageType, productCode, quantity, uom, timestamp);
    }


}