package com.epam.validator;

import com.epam.queue.message.InstructionMessage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static com.epam.queue.message.MessageType.A;
import static java.time.temporal.ChronoUnit.DAYS;

public class InstructionMessageValidatorTest {

    private static final String PRODUCT_CODE = "MZ89";
    private static final String WRONG_PRODUCT_CODE = "ZM_89";
    private static final String WRONG_PRODUCT_CODE_LOWERCASE = "zm89";
    private static final String WRONG_PRODUCT_CODE_WITHOUT_DIGITS = "ZM";

    private static final Integer QUANTITY = 99;
    private static final Integer WRONG_QUANTITY_MIN_VALID_VALUE = 1;
    private static final Integer WRONG_QUANTITY_BELOW_MIN_VALID_VALUE = 0;

    private static final Integer UOM = 50;
    private static final Integer UOM_MAX_VALID_VALUE = 255;
    private static final Integer WRONG_UOM_ABOVE_MAX_VALID_VALUE = 256;
    private static final Integer UOM_MIN_VALID_VALUE = 0;
    private static final Integer WRONG_UOM_BELOW_MIN_VALID_VALUE = -1;

    private static final Date CORRECT_TIMESTAMP = new Date(1);
    private static final Date DATE_UNIX_EPOCH = new Date(0);
    private static final Date DATE_IN_FUTURE = createDateInFuture();

    private InstructionMessageValidator messageValidator;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws ParseException {
        messageValidator = new InstructionMessageValidator();
    }

    @Test
    public void shouldNotThrowExceptionWhenMessageIsCorrect() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("Product code is not valid Expected:(two uppercase letters before by two digits), Actual: " + WRONG_PRODUCT_CODE);
        messageValidator.validate(new InstructionMessage(A, WRONG_PRODUCT_CODE, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithProductCodeInLowercase() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("Product code is not valid Expected:(two uppercase letters before by two digits), Actual: " + WRONG_PRODUCT_CODE_LOWERCASE);
        messageValidator.validate(new InstructionMessage(A, WRONG_PRODUCT_CODE_LOWERCASE, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithProductCodeWithoutDigits() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("Product code is not valid Expected:(two uppercase letters before by two digits), Actual: " + WRONG_PRODUCT_CODE_WITHOUT_DIGITS);
        messageValidator.validate(new InstructionMessage(A, WRONG_PRODUCT_CODE_WITHOUT_DIGITS, QUANTITY, UOM, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldNotThrowExceptionWhenMessageWithMinValueQuantity() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, WRONG_QUANTITY_MIN_VALID_VALUE, UOM, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithBelowMinValidValueQuantity() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("Quantity is not valid: should not be less then 0");
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, WRONG_QUANTITY_BELOW_MIN_VALID_VALUE, UOM, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldNotThrowExceptionWhenMessageWithUOMMaxValidValue() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM_MAX_VALID_VALUE, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithUOMAboveMaxValidValue() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("UOM is not valid, should be between 0 and 256");
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, WRONG_UOM_ABOVE_MAX_VALID_VALUE, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldNotThrowExceptionWhenMessageWithUOMMinValidValue() {
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM_MIN_VALID_VALUE, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithUOMBelowMinValidValue() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("UOM is not valid, should be between 0 and 256");
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, WRONG_UOM_BELOW_MIN_VALID_VALUE, CORRECT_TIMESTAMP));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithTimestampUnixEpoch() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("Date is not valid: timestamp shouldn't be lass than Unix epoch and more than current data time");
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM, DATE_UNIX_EPOCH));
    }

    @Test()
    public void shouldThrowExceptionWhenMessageWithTimestampInFuture() {
        expectedEx.expect(MessageValidationException.class);
        expectedEx.expectMessage("Date is not valid: timestamp shouldn't be lass than Unix epoch and more than current data time");
        messageValidator.validate(new InstructionMessage(A, PRODUCT_CODE, QUANTITY, UOM, DATE_IN_FUTURE));
    }

    private static Date createDateInFuture() {
        return Date.from(Instant.now().plus(1, DAYS));
    }

}