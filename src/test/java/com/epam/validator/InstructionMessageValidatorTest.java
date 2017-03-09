package com.epam.validator;

import com.epam.queue.message.InstructionMessage;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static com.epam.queue.message.MessageType.A;

public class InstructionMessageValidatorTest {

    private static final String PRODUCT_CODE = "MZ89";
    private static final String WRONG_PRODUCT_CODE = "ZM_89";
    private static final String WRONG_PRODUCT_CODE_LOWERCASE = "zm89";
    private static final String WRONG_PRODUCT_CODE_WITHOUT_DIGITS = "ZM";

    private static final Integer QUANTITY = 99;
    private static final Integer WRONG_QUANTITY_MIN_VALUE = 0;
    private static final Integer WRONG_QUANTITY_BELOW_MIN_VALUE = -1;

    private static final Integer UOM = 50;
    private static final Integer WRONG_UOM_ABOVE_MAX_VALUE = 256;
    private static final Integer WRONG_UOM_BELOW_MIN_VALUE = -1;

    private static final Date CORRECT_TIMESTAMP = new Date(1);
    private static final Date DATE_BEFORE_UNIX_EPOCH = new Date(-1);
    private static final Date DATE_IN_FUTURE = new Date(System.currentTimeMillis() + 999999);

    private InstructionMessageValidator messageValidator;

    @Before
    public void setUp() throws ParseException {
        messageValidator = new InstructionMessageValidator();
    }

    @Test
    public void shouldNotThrowExceptionWhenMessageIsCorrect() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() {
        messageValidator.validate(new InstructionMessage(A, WRONG_PRODUCT_CODE, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithProductCodeInLowercase() {
        messageValidator.validate(new InstructionMessage(A, WRONG_PRODUCT_CODE_LOWERCASE, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithProductCodeWithoutDigits() {
        messageValidator.validate(new InstructionMessage(A, WRONG_PRODUCT_CODE_WITHOUT_DIGITS, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithMinValueQuantity() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, WRONG_QUANTITY_MIN_VALUE, UOM, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithBelowMinValueQuantity() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, WRONG_QUANTITY_BELOW_MIN_VALUE, UOM, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUOMAboveMaxValue() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, WRONG_UOM_ABOVE_MAX_VALUE, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUOMBelowMinValue() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, WRONG_UOM_BELOW_MIN_VALUE, CORRECT_TIMESTAMP));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithTimestampBeforeUnixEpoch() {

        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM, DATE_BEFORE_UNIX_EPOCH));
    }

    @Test(expected = MessageValidationException.class)
    public void shouldThrowExceptionWhenMessageWithTimestampInFuture() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM, DATE_IN_FUTURE));
    }

}