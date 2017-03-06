package com.epam.validator;

import com.epam.queue.message.InstructionMessage;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class InstructionMessageValidator {

    private static final String VALIDATION_ERROR_MESSAGE = "Validation error: %s";

    private static final String PRODUCT_CODE_REGEXP = "^[A-Z]{2}\\d{2}$";
    private static final int MAX_VALUE_UOM = 256;
    private static final int MIN_VALUE_UOM = 0;

    private static final Date UNIX_EPOCH = new Date(0);

    private static final int MIN_VALUE_QUANTITY = 1;

    private static final Pattern PRODUCT_CODE_PATTERN = Pattern.compile(PRODUCT_CODE_REGEXP);


    public void validate(InstructionMessage instructionMessage) {
        validateProductCode(instructionMessage);
        validateQuantity(instructionMessage);
        validateUom(instructionMessage);
        validateTimestamp(instructionMessage);
    }

    private void validateProductCode(InstructionMessage message) {
        if (!isValidProductCode(message.getProductCode())) {
            throw new MessageValidationException(format(VALIDATION_ERROR_MESSAGE, "product code is not valid Expected:(two uppercase letters before by two digits), Actual: " + message.getProductCode()));
        }
    }

    private void validateQuantity(InstructionMessage message) {
        if (message.getQuantity() < MIN_VALUE_QUANTITY) {
            throw new MessageValidationException(format(VALIDATION_ERROR_MESSAGE, "quantity is not valid: should not be lass then 0"));
        }
    }

    private void validateUom(InstructionMessage message) throws MessageValidationException {
        if (message.getUom() < MIN_VALUE_UOM || message.getUom() >= MAX_VALUE_UOM) {
            throw new MessageValidationException(format(VALIDATION_ERROR_MESSAGE, "UOM is not valid, should be between " + MIN_VALUE_UOM + " and " + MAX_VALUE_UOM));
        }
    }

    private void validateTimestamp(InstructionMessage message) {
        Date dateTime = message.getTimestamp();
        if (dateTime.before(UNIX_EPOCH) || dateTime.equals(UNIX_EPOCH) || dateTime.after(new Date())) {
            throw new MessageValidationException(format(VALIDATION_ERROR_MESSAGE, "date is not valid: timestamp shouldn't be lass than Unix epoch and more than current data time"));
        }
    }

    private boolean isValidProductCode(String productCode) {
        Matcher productCodeMatcher = PRODUCT_CODE_PATTERN.matcher(productCode);
        return productCodeMatcher.matches();
    }

}
