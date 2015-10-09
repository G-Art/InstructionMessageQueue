package com.epam.test.validator;

import com.epam.data.InstructionMessage;
import com.epam.validation.ValidationException;
import com.epam.validation.impl.InstructionMessageValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class InstructionMessageValidatorTest {

    @Resource(name = "instructionValidator")
    private InstructionMessageValidator underTest;

    @Resource(name = "dateFormat")
    private String dateFormatPattern;

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
        dateFormat = new SimpleDateFormat(dateFormatPattern);

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
        assertTrue(underTest.validate(correctMessage));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectInstructionType() throws ValidationException {
        assertTrue(underTest.validate(messageWithIncorrectInstructionType));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectProductCode() throws ValidationException {
        assertTrue(underTest.validate(messageWithIncorrectProductCode));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectQuantity() throws ValidationException {
        assertTrue(underTest.validate(messageWithIncorrectQuantity));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectUOM() throws ValidationException {
        assertTrue(underTest.validate(messageWithIncorrectUOM));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithNullTimestamp() throws ValidationException {
        assertTrue(underTest.validate(messageWithNullTimestamp));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenMessageWithIncorrectTimestamp() throws ValidationException {
        assertTrue(underTest.validate(messageWithIncorrectTimestamp));
    }
}