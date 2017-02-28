package com.epam.validator;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static com.epam.queue.message.InstructionMessage.*;
import static org.junit.Assert.assertEquals;

public class InstructionMessageValidatorTest {

    private final static String SPRITTING_REGEXP = " ";

    private static final String CORRECT_MESSAGE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";

    private static final String MESSAGE_WITH_INCORRECT_TYPE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_PRODUCT_CODE_IN_LOWER_CASE = "InstructionMessage A mz89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_INCORRECT_QUANTITY = "InstructionMessage A MZ89 -1 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_UOM_GRATER_255 = "InstructionMessage A MZ89 5678 256 2015-03-05T10:04:56.012Z";

    private static final String MESSAGE_WITHOUT_TYPE = "InstructionMessage MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITHOUT_PRODUCT_CODE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITHOUT_QUANTITY = "InstructionMessage A MZ89 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITHOUT_UOM = "InstructionMessage A MZ89 5678 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITHOUT_TIMESTAMP = "InstructionMessage A MZ89 5678 50";

    private InstructionMessageValidator messageValidator;

    @Before
    public void setUp() throws ParseException {
        messageValidator = new InstructionMessageValidator();
    }

    @Test
    public void shouldNotThrowExceptionWhenMessageIsCorrect() throws ValidationException {
        messageValidator.validate(CORRECT_MESSAGE.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutInstructionType() {
        messageValidator.validate(MESSAGE_WITHOUT_TYPE.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectInstructionType() {
        messageValidator.validate(MESSAGE_WITH_INCORRECT_TYPE.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutProductCode() {
        messageValidator.validate(MESSAGE_WITHOUT_PRODUCT_CODE.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() {
        messageValidator.validate(MESSAGE_WITH_PRODUCT_CODE_IN_LOWER_CASE.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutQuantity() {
        messageValidator.validate(MESSAGE_WITHOUT_QUANTITY.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() {
        messageValidator.validate(MESSAGE_WITH_INCORRECT_QUANTITY.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutUOM() {
        messageValidator.validate(MESSAGE_WITHOUT_UOM.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectUOM() {
        messageValidator.validate(MESSAGE_WITH_UOM_GRATER_255.split(SPRITTING_REGEXP));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutTimestamp() {
        messageValidator.validate(MESSAGE_WITHOUT_TIMESTAMP.split(SPRITTING_REGEXP));
    }


}