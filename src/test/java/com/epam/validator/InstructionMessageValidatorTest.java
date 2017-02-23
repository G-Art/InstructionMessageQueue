package com.epam.validator;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static com.epam.queue.message.InstructionMessage.*;
import static org.junit.Assert.assertEquals;

public class InstructionMessageValidatorTest {

    public static final String CORRECT_MESSAGE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";

    public static final String MESSAGE_WITH_INCORRECT_TYPE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_PRODUCT_CODE_IN_LOWER_CASE = "InstructionMessage A mz89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_INCORRECT_QUANTITY = "InstructionMessage A MZ89 -1 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_UOM_GRATER_255 = "InstructionMessage A MZ89 5678 256 2015-03-05T10:04:56.012Z";

    public static final String MESSAGE_WITHOUT_TYPE = "InstructionMessage MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_PRODUCT_CODE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_QUANTITY = "InstructionMessage A MZ89 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_UOM = "InstructionMessage A MZ89 5678 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_TIMESTAMP = "InstructionMessage A MZ89 5678 50";

    private InstructionMessageValidator messageValidator;

    @Before
    public void setUp() throws ParseException {
        messageValidator = new InstructionMessageValidator();
    }

    @Test
    public void shouldValidateWhenMessageIsCorrect() throws ValidationException {
        String [] splitedMessage = messageValidator.validate(CORRECT_MESSAGE);

        assertEquals(splitedMessage[INSTRUCTION_PREFIX_POSITION], "InstructionMessage");
        assertEquals(splitedMessage[INSTRUCTION_TYPE_POSITION], "A");
        assertEquals(splitedMessage[PRODUCT_CODE_POSITION], "MZ89");
        assertEquals(splitedMessage[QUANTITY_POSITION], "5678");
        assertEquals(splitedMessage[UOM_POSITION], "50");
        assertEquals(splitedMessage[TIMESTAMP_POSITION], "2015-03-05T10:04:56.012Z");

    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutInstructionType() {
        messageValidator.validate(MESSAGE_WITHOUT_TYPE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectInstructionType() {
        messageValidator.validate(MESSAGE_WITH_INCORRECT_TYPE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutProductCode() {
        messageValidator.validate(MESSAGE_WITHOUT_PRODUCT_CODE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() {
        messageValidator.validate(MESSAGE_WITH_PRODUCT_CODE_IN_LOWER_CASE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutQuantity() {
        messageValidator.validate(MESSAGE_WITHOUT_QUANTITY);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() {
        messageValidator.validate(MESSAGE_WITH_INCORRECT_QUANTITY);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutUOM() {
        messageValidator.validate(MESSAGE_WITHOUT_UOM);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectUOM() {
        messageValidator.validate(MESSAGE_WITH_UOM_GRATER_255);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutTimestamp() {
        messageValidator.validate(MESSAGE_WITHOUT_TIMESTAMP);
    }

}