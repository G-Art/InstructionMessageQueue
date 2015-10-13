package com.epam.validation.impl;

import com.epam.data.InstructionMessage;
import com.epam.queue.PriorityType;
import com.epam.validation.ValidationException;
import com.epam.validation.Validator;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionMessageValidator implements Validator<InstructionMessage> {

    private static final String PRODUCT_CODE_PATTERN = "^[A-Z]{2}\\d{2}$";

    @Override
    public void validate(InstructionMessage o) throws ValidationException {
            validateFields(o);
    }

    private void validateFields(InstructionMessage instructionMessage) throws ValidationException {

        validateInstructionType(instructionMessage);
        validateProductCode(instructionMessage);
        validateTimestamp(instructionMessage);

        if (instructionMessage.getQuantity() < 0) {
            throw new ValidationException("Error quantity must be positive");
        }

        if (instructionMessage.getUom() <= 0 || instructionMessage.getUom() > 256) {
            throw new ValidationException("Error uom must be positive and less then 256");
        }

    }

    private boolean isValidInstructionType(String o) {
        return PriorityType.valueOf(o) != null;
    }

    private boolean isValidProductCode(String value) {
        Pattern r       = Pattern.compile(PRODUCT_CODE_PATTERN);
        Matcher matcher = r.matcher(value);
        return matcher.matches();
    }

    private void validateInstructionType(InstructionMessage instructionMessage) throws ValidationException {
        if (StringUtils.isEmpty(instructionMessage.getInstructionType())) {
            throw new ValidationException("Error InstructionType field is empty or null");
        }

        if (!isValidInstructionType(instructionMessage.getInstructionType())) {
            throw new ValidationException("Error InstructionType is not valid");
        }
    }

    private void validateProductCode(InstructionMessage instructionMessage) throws ValidationException {
        if (StringUtils.isEmpty(instructionMessage.getProductCode())) {
            throw new ValidationException("Error ProductCode field is empty or null");
        }

        if (!isValidProductCode(instructionMessage.getProductCode())) {
            throw new ValidationException("Error invalid ProductCode");
        }
    }

    private void validateTimestamp(InstructionMessage instructionMessage) throws ValidationException {

        if (instructionMessage.getTimestamp() == null) {
            throw new ValidationException("Error date mustn't be null");
        }

        if (instructionMessage.getTimestamp().getTime() <= 0 || instructionMessage.getTimestamp().getTime() > new Date().getTime()) {
            throw new ValidationException("Error date is not valid");
        }
    }

}
