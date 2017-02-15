package com.epam.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtilsTest {

    @Test
    public void shouldCorrectlyParseData() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String STRING_DATA_REPRESENTATION = "2015-03-05T10:04:56.012Z";

        Date expectedDate = dateFormat.parse(STRING_DATA_REPRESENTATION);
        Date actualDate = DateTimeUtils.parse(STRING_DATA_REPRESENTATION);

        Assert.assertEquals(actualDate, expectedDate);
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionIfTimestampFormatIsNotCorrect() throws Exception {
         String STRING_DATA_REPRESENTATION = "2015/03/05T10:04:56.012Z";
         DateTimeUtils.parse(STRING_DATA_REPRESENTATION);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfTimestampIsNull() throws Exception {
        DateTimeUtils.parse(null);
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionIfTimestampIsEmpty() throws Exception {
        DateTimeUtils.parse("");
    }
}
