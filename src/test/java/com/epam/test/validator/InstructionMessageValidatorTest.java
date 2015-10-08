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

    @Before
    public void setUp(){
        dateFormat = new SimpleDateFormat(dateFormatPattern);
    }

    @Test
    public void shouldBeValid() throws ParseException, ValidationException {
        assertTrue(underTest.validate(new InstructionMessage("A", "MZ89", 5678, 50, dateFormat.parse("2015-03-05T10:04:51.012Z"))));
    }

    @Test(expected = ValidationException.class)
    public void shouldBeException() throws ParseException, ValidationException {
        assertTrue(underTest.validate(new InstructionMessage("", "", 5678, 50, dateFormat.parse("2015-03-05T10:04:51.012Z"))));
    }
}