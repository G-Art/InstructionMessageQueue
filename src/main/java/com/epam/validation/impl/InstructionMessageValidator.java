package com.epam.validation.impl;

import com.epam.data.InstructionMessage;
import com.epam.validation.ValidationException;
import com.epam.validation.Validator;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionMessageValidator implements Validator {

    private Map<String, Integer> instructions;
    private String               productCodePattern;

    public InstructionMessageValidator(Map<String, Integer> instructions, String productCodePattern) {
        this.instructions = instructions;
        this.productCodePattern = productCodePattern;
    }

    public InstructionMessageValidator() {
    }

    @Override
    public boolean validate(Object o) throws ValidationException {
        if (o instanceof InstructionMessage) {
            return validateFields((InstructionMessage) o);
        }
        throw new IllegalArgumentException(
                "Error expected type: (InstructionMessage) actual type: (" + o.getClass() + ")");
    }

    private boolean validateFields(InstructionMessage o) throws ValidationException {

        if (StringUtils.isEmpty(o.getInstructionType())) {
            throw new ValidationException("Error InstructionType field is empty or null");
        }

        if (isNotValidInstructionType(o.getInstructionType())) {
            throw new ValidationException("Error InstructionType is not valid");
        }

        if (StringUtils.isEmpty(o.getProductCode())) {
            throw new ValidationException("Error ProductCode field is empty or null");
        }

        if (!isValidProductCode(o.getProductCode())) {
            throw new ValidationException("Error invalid ProductCode");
        }

        if (o.getQuantity() < 0) {
            throw new ValidationException("Error quantity must be positive");
        }

        if (o.getUom() <= 0 || o.getUom() > 256) {
            throw new ValidationException("Error uom must be positive and less then 256");
        }

        if (o.getTimestamp() == null) {
            throw new ValidationException("Error date mustn't be null");
        }

        if (o.getTimestamp().getTime() <= 0 || o.getTimestamp().getTime() > new Date().getTime()) {
            throw new ValidationException("Error date is not valid");
        }

        return true;
    }

    private boolean isNotValidInstructionType(String o) {
        return !instructions.keySet().contains(o);
    }

    private boolean isValidProductCode(String value) {
        Pattern r       = Pattern.compile(productCodePattern);
        Matcher matcher = r.matcher(value);
        return matcher.matches();
    }

    @Required
    public void setInstructions(Map<String, Integer> instructions) {
        this.instructions = instructions;
    }

    @Required
    public void setProductCodePattern(String productCodePattern) {
        this.productCodePattern = productCodePattern;
    }
}
