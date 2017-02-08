package com.epam.validator;

import com.epam.data.InstructionMessage;
import com.epam.queue.MessageType;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionMessageValidator{

    private final String PRODUCT_CODE_PATTERN = "^[A-Z]{2}\\d{2}$";
    private final int    MAX_VALUE_UOM        = 256;
    private final int    MIN_VALUE_QUANTITY   = 0;

    private final Pattern pattern = Pattern.compile(PRODUCT_CODE_PATTERN);

    public void validate(InstructionMessage instructionMessage) throws ValidationException {

        validateInstructionType(instructionMessage);
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
        if (instructionMessage.getUom() <= 0) {
            throw new ValidationException("UOM must be positive");
        }

        if (instructionMessage.getUom() > MAX_VALUE_UOM) {
            throw new ValidationException("UOM must be less then: " + MAX_VALUE_UOM + ". Actual UOM is:" + instructionMessage.getUom());
        }
    }

    private boolean isValidInstructionType(String instructionType) {
        return Arrays.stream(MessageType.values()).map(MessageType::name).anyMatch(instructionType::equals);
    }

    private boolean isValidProductCode(String value) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private void validateInstructionType(InstructionMessage instructionMessage) throws ValidationException {
        if (StringUtils.isEmpty(instructionMessage.getInstructionType())) {
            throw new ValidationException("InstructionType field is empty or null");
        }

        if (!isValidInstructionType(instructionMessage.getInstructionType())) {
            throw new ValidationException("InstructionType is not valid");
        }
    }

    private void validateProductCode(InstructionMessage instructionMessage) throws ValidationException {
        if (StringUtils.isEmpty(instructionMessage.getProductCode())) {
            throw new ValidationException("ProductCode field is empty or null");
        }

        if (!isValidProductCode(instructionMessage.getProductCode())) {
            throw new ValidationException("Invalid ProductCode");
        }
    }

    private void validateTimestamp(InstructionMessage instructionMessage) throws ValidationException {

        if (instructionMessage.getTimestamp() == null) {
            throw new ValidationException("Date mustn't be null");
        }

        if (instructionMessage.getTimestamp().after(new Date())) {
            throw new ValidationException("Date is not valid");
        }
    }

}
