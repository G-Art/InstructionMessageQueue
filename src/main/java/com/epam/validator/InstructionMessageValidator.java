package com.epam.validator;

import com.epam.data.InstructionMessage;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionMessageValidator{

    private final String PRODUCT_CODE_PATTERN = "^[A-Z]{2}\\d{2}$";
    private final int    MAX_VALUE_UOM        = 256;
    private final int    MIN_VALUE_QUANTITY   = 0;

    private final Pattern pattern = Pattern.compile(PRODUCT_CODE_PATTERN);

    public void validate(InstructionMessage instructionMessage) throws ValidationException {

        validateProductCode(instructionMessage);
        validateTimestamp(instructionMessage);
        validateUom(instructionMessage);
        validateQuantity(instructionMessage);

    }

    private void validateQuantity(InstructionMessage instructionMessage) throws ValidationException {
        if (instructionMessage.getQuantity() < MIN_VALUE_QUANTITY) {
            throw new ValidationException("Quantity must be positive");
        }
    }

    private void validateUom(InstructionMessage instructionMessage) throws ValidationException {
        if (instructionMessage.getUom() < 0) {
            throw new ValidationException("UOM must be positive");
        }

        if (instructionMessage.getUom() >= MAX_VALUE_UOM) {
            throw new ValidationException("UOM must be less then: " + MAX_VALUE_UOM + ". Actual UOM is:" + instructionMessage.getUom());
        }
    }

    private boolean isValidProductCode(String value) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private void validateProductCode(InstructionMessage instructionMessage) throws ValidationException {
        if (!isValidProductCode(instructionMessage.getProductCode())) {
            throw new ValidationException("Invalid ProductCode");
        }
    }

    private void validateTimestamp(InstructionMessage instructionMessage) throws ValidationException {
        if (instructionMessage.getTimestamp().after(new Date())) {
            throw new ValidationException("Date is not valid");
        }
    }

}
