package com.epam.test.validator;

import com.epam.Constants;
import com.epam.data.InstructionMessage;
import com.epam.validation.ValidationException;
import com.epam.validation.Validator;
import com.epam.validation.impl.InstructionMessageValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InstructionMessageValidatorTest {

    private Validator<InstructionMessage> underTest;

    private SimpleDateFormat dateFormat;

    private InstructionMessage correctMessage;
    private InstructionMessage messageWithIncorrectInstructionType;
    private InstructionMessage messageWithIncorrectProductCode;
    private InstructionMessage messageWithIncorrectQuantity;
    private InstructionMessage messageWithIncorrectUOM;
    private InstructionMessage messageWithNullTimestamp;
    private InstructionMessage messageWithIncorrectTimestamp;


    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

        underTest = new InstructionMessageValidator();

        correctMessage = new InstructionMessage("A", "AZ19", 11, 11, dateFormat.parse("2015-03-05T10:04:51.012Z"));

        messageWithIncorrectInstructionType = new InstructionMessage("", "AZ19", 1111, 11,
                                                                     dateFormat.parse("2015-03-05T10:04:51.012Z"));
        messageWithIncorrectProductCode = new InstructionMessage("A", "0000", 1111, 11,
                                                                 dateFormat.parse("2015-03-05T10:04:51.012Z"));
        messageWithIncorrectQuantity = new InstructionMessage("A", "AZ19", -1111, 11,
                                                              dateFormat.parse("2015-03-05T10:04:51.012Z"));
        messageWithIncorrectUOM = new InstructionMessage("A", "AZ19", 1111, -11,
                                                         dateFormat.parse("2015-03-05T10:04:51.012Z"));
        messageWithIncorrectTimestamp = new InstructionMessage("A", "AZ19", 1111, 11,
                                                               dateFormat.parse("3000-03-05T10:04:51.012Z"));
        messageWithNullTimestamp = new InstructionMessage("A", "AZ19", 1111, 11, null);

    }

    @Test
    public void shouldAcceptWhenMessageIsCorrect() throws ValidationException {
        try{
            underTest.validate(correctMessage);
        }catch (Exception e){
            Assert.fail();
        }

    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectInstructionType() throws ValidationException {
        underTest.validate(messageWithIncorrectInstructionType);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() throws ValidationException {
        underTest.validate(messageWithIncorrectProductCode);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() throws ValidationException {
        underTest.validate(messageWithIncorrectQuantity);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectUOM() throws ValidationException {
        underTest.validate(messageWithIncorrectUOM);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithNullTimestamp() throws ValidationException {
        underTest.validate(messageWithNullTimestamp);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectTimestamp() throws ValidationException {
        underTest.validate(messageWithIncorrectTimestamp);
    }
}