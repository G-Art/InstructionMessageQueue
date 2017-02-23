package com.epam.receiver;

import com.epam.queue.InstructionQueue;
import com.epam.queue.message.InstructionMessage;
import com.epam.receiver.impl.DefaultMessageReceiver;
import com.epam.validator.InstructionMessageValidator;
import com.epam.validator.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.epam.queue.message.InstructionMessage.DATE_FORMAT;
import static com.epam.queue.message.MessageType.A;
import static org.junit.Assert.assertEquals;

public class DefaultMessageReceiverTest {

    public static final String CORRECT_MESSAGE_A_TYPE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";

    public static final String MESSAGE_WITH_INCORRECT_PREFIX = "InstructionM A MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_INCORRECT_TYPE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_PRODUCT_CODE_IN_LOWER_CASE = "InstructionMessage A mz89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_PRODUCT_CODE_WITHOUT_DIGITS = "InstructionMessage A MZ 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_UOM_GRATER_255 = "InstructionMessage A MZ89 5678 256 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_UOM_LESS_0 = "InstructionMessage A MZ89 5678 -1 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITH_INCORRECT_TIMESTAMP_FORMAT = "InstructionMessage A MZ89 5678 50 2015/03/05T10:04:56.012Z";

    public static final String MESSAGE_WITHOUT_PREFIX = "A MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_TYPE = "InstructionMessage MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_PRODUCT_CODE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_QUANTITY = "InstructionMessage A MZ89 50 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_UOM = "InstructionMessage A MZ89 5678 2015-03-05T10:04:56.012Z";
    public static final String MESSAGE_WITHOUT_TIMESTAMP = "InstructionMessage A MZ89 5678 50";

    private DefaultMessageReceiver messageReceiver;

    @Before
    public void setUp() throws Exception {
        InstructionMessageValidator instructionMessageValidator = new InstructionMessageValidator();

        messageReceiver = new DefaultMessageReceiver();
        messageReceiver.setValidator(instructionMessageValidator);
        messageReceiver.setQueue(new InstructionQueue());
    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException, ParseException {
        messageReceiver.receive(CORRECT_MESSAGE_A_TYPE);
    }

    @Test
    public void shouldAcceptWhenReceiveTwoEqualsCorrectMessages() throws ValidationException, ParseException {
        messageReceiver.receive(CORRECT_MESSAGE_A_TYPE);
        messageReceiver.receive(CORRECT_MESSAGE_A_TYPE);
    }

    @Test
    public void shouldCorrectlyParseInstructionMessage() throws ParseException {
        InstructionMessage instructionMessage = new InstructionMessage(CORRECT_MESSAGE_A_TYPE.split(" "));

        assertEquals(A, instructionMessage.getInstructionType());
        assertEquals("MZ89", instructionMessage.getProductCode());
        assertEquals(5678, instructionMessage.getQuantity());
        assertEquals(50, instructionMessage.getUom());
        assertEquals(new SimpleDateFormat(DATE_FORMAT).parse("2015-03-05T10:04:56.012Z"), instructionMessage.getTimestamp());
    }


    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectPrefix() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITH_INCORRECT_PREFIX);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutPrefix() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITHOUT_PREFIX);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageContainIncorrectMessageType() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITH_INCORRECT_TYPE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutMessageType() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITHOUT_TYPE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutProductCode() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITHOUT_PRODUCT_CODE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWhereProductCodeInLowerCase() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITH_PRODUCT_CODE_IN_LOWER_CASE);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWhereProductCodeWithoutDigits() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITH_PRODUCT_CODE_WITHOUT_DIGITS);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutQuantity() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITHOUT_QUANTITY);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutUOM() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITHOUT_UOM);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUomGraterThan255() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITH_UOM_GRATER_255);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithUomLessThan0() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITH_UOM_LESS_0);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithoutTimestamp() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITHOUT_TIMESTAMP);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectTimestampFormat() throws ValidationException, ParseException {
        messageReceiver.receive(MESSAGE_WITH_INCORRECT_TIMESTAMP_FORMAT);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageIsNull() throws ValidationException, ParseException {
        messageReceiver.receive(null);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageIsEmptyString() throws ValidationException, ParseException {
        messageReceiver.receive("");
    }
}