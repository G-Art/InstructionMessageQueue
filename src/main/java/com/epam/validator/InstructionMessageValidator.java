package com.epam.validator;

import com.epam.queue.message.MessageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.queue.message.InstructionMessage.*;
import static java.lang.Integer.valueOf;
import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

public class InstructionMessageValidator {

    private static final String VALIDATION_ERROR_MESSAGE = "Validation error: %s";

    private static final String MESSAGE_PREFIX = "InstructionMessage";

    private static final String PRODUCT_CODE_PATTERN = "^[A-Z]{2}\\d{2}$";
    private static final int MAX_VALUE_UOM = 256;
    private static final int MIN_VALUE_UOM = 0;
    private static final int MIN_VALUE_QUANTITY = 0;

    private static final int MESSAGE_PARAMETER_COUNT = 6;

    private static final Pattern pattern = Pattern.compile(PRODUCT_CODE_PATTERN);


    public void validate(String[] splittedMessage){
            validateCountParameters(splittedMessage);
            validateInstructionMessagePrefix(splittedMessage[INSTRUCTION_PREFIX_POSITION]);
            validateMessageType(splittedMessage[INSTRUCTION_TYPE_POSITION]);
            validateProductCode(splittedMessage[PRODUCT_CODE_POSITION]);
            validateQuantity(splittedMessage[QUANTITY_POSITION]);
            validateUom(splittedMessage[UOM_POSITION]);
            validateTimestamp(splittedMessage[TIMESTAMP_POSITION]);
    }

    private void validateCountParameters(String[] splittedMessage){
        if (splittedMessage.length != MESSAGE_PARAMETER_COUNT) {
            throw new ValidationException(format(VALIDATION_ERROR_MESSAGE, "data count is not consistent Example: InstructionMessage <InstructionType> <ProductCode> <Quantity> <UOM> <Timestamp>"));
        }
    }


    private void validateInstructionMessagePrefix(String messagePrefix){
        if (isEmpty(messagePrefix) || !messagePrefix.equals(MESSAGE_PREFIX)) {
            throw new ValidationException(format(VALIDATION_ERROR_MESSAGE, "message prefix is not valid Expected: " + MESSAGE_PREFIX + " Actual: " + messagePrefix));
        }
    }

    private void validateMessageType(String messageType) {
        MessageType.valueOf(messageType);
    }

    private void validateQuantity(String quantity){
        if (valueOf(quantity) < MIN_VALUE_QUANTITY) {
            throw new ValidationException(format(VALIDATION_ERROR_MESSAGE, "quantity is not valid: should not be lass then 0"));
        }
    }

    private void validateUom(String uom) throws ValidationException {
        if (valueOf(uom) < MIN_VALUE_UOM || valueOf(uom) >= MAX_VALUE_UOM) {
            throw new ValidationException(format(VALIDATION_ERROR_MESSAGE, "UOM is not valid, should be between " + MIN_VALUE_UOM + " and "+ MAX_VALUE_UOM));
        }
    }

    private void validateProductCode(String productCode){
        if (!isValidProductCode(productCode)) {
            throw new ValidationException(format(VALIDATION_ERROR_MESSAGE, "product code is not valid Expected:(two uppercase letters before by two digits), Actual: " + productCode));
        }
    }

    private void validateTimestamp(String timestamp){
        try {
            Date dateTime = new SimpleDateFormat(DATE_FORMAT).parse(timestamp);
            if (dateTime.before(new Date(0)) || dateTime.after(new Date())) {
                throw new ValidationException(format(VALIDATION_ERROR_MESSAGE, "date is not valid: timestamp shouldn't be lass than Unix epoch and more than current data time"));
            }
        } catch (ParseException e) {
            throw new ValidationException(e);
        }
    }

    private boolean isValidProductCode(String productCode) {
        Matcher productCodeMatcher = pattern.matcher(productCode);
        return productCodeMatcher.matches();
    }

}
