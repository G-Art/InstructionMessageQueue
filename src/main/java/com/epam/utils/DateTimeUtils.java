package com.epam.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Date parse(String date) throws ParseException {
        return new SimpleDateFormat(DATE_FORMAT).parse(date);
    }

}
