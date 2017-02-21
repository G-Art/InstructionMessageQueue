package com.epam.validator;

import com.epam.queue.MessageType;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionMessageValidator {

    private static final String MESSAGE_PREFIX = "InstructionMessage";

    private static final String PRODUCT_CODE_PATTERN = "^[A-Z]{2}\\d{2}$";
    private static final int MAX_VALUE_UOM = 256;
    private static final int MIN_VALUE_QUANTITY = 0;

    private static final Pattern pattern = Pattern.compile(PRODUCT_CODE_PATTERN);

    private String dateFormat;


    public String [] validate(String message) throws ValidationException {
        try {
            String[] splittedMessage = message.split(" ");

            validateInstructionMessagePrefix(splittedMessage[0]);
            validateMessageType(splittedMessage[1]);
            validateProductCode(splittedMessage[2]);
            validateQuantity(splittedMessage[3]);
            validateUom(splittedMessage[4]);
            validateTimestamp(splittedMessage[5]);

            return splittedMessage;
        } catch (RuntimeException e) {
            throw new ValidationException(e);
        }
    }

    private void validateMessageType(String messageType) {
        MessageType.valueOf(messageType);
    }

    private void validateQuantity(String quantity) throws ValidationException {
        if (Integer.valueOf(quantity) < MIN_VALUE_QUANTITY) {
            throw new ValidationException("Quantity must be grater then " + MIN_VALUE_QUANTITY);
        }
    }

    private void validateUom(String uom) throws ValidationException {
        Integer intUom = Integer.parseInt(uom);

        if (intUom < 0) {
            throw new ValidationException("UOM must be grater then 0" + ". Actual UOM is:" + intUom);
        }

        if (intUom >= MAX_VALUE_UOM) {
            throw new ValidationException("UOM must be less then: " + MAX_VALUE_UOM + ". Actual UOM is:" + intUom);
        }
    }

    private void validateProductCode(String productCode) throws ValidationException {
        if (!isValidProductCode(productCode)) {
            throw new ValidationException("Invalid ProductCode Expected:(two uppercase letters before by two digits), Actual: " + productCode);
        }
    }

    private void validateTimestamp(String timestamp) throws ValidationException {
        try {
            Date dateTime = new SimpleDateFormat(dateFormat).parse(timestamp);
            if (dateTime.before(new Date(0)) || dateTime.after(new Date())) {
                throw new ValidationException("Date is not valid: timestamp shouldn't be in future");
            }
        } catch (ParseException e) {
            throw new ValidationException(e);
        }
    }

    private void validateInstructionMessagePrefix(String messagePrefix) {
        if (StringUtils.isEmpty(messagePrefix)) {
            throw new IllegalArgumentException("Message shouldn't be empty or null ");
        }

        if (!messagePrefix.equals(MESSAGE_PREFIX)) {
            throw new IllegalArgumentException("Message should start with: (" + MESSAGE_PREFIX + ")");
        }
    }

    private boolean isValidProductCode(String productCode) {
        Matcher productCodeMatcher = pattern.matcher(productCode);
        return productCodeMatcher.matches();
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
