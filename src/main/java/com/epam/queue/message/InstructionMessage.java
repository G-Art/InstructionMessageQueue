package com.epam.queue.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public final class InstructionMessage {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final int INSTRUCTION_PREFIX_POSITION = 0;
    public static final int INSTRUCTION_TYPE_POSITION = 1;
    public static final int PRODUCT_CODE_POSITION = 2;
    public static final int QUANTITY_POSITION = 3;
    public static final int UOM_POSITION = 4;
    public static final int TIMESTAMP_POSITION = 5;

    private final MessageType instructionType;
    private final String productCode;
    private final int quantity;
    private final int uom;
    private final Date timestamp;


    private InstructionMessage(MessageType messageType, String productCode, int quantity, int uom, Date timestamp) throws ParseException {
        this.instructionType =  messageType;
        this.productCode = productCode;
        this.quantity = quantity;
        this.uom = uom;
        this.timestamp = timestamp;
    }

    public MessageType getInstructionType() {
        return instructionType;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUom() {
        return uom;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public static final class MessageBuilder{
        private MessageBuilder() {
        }

        public static InstructionMessage build(String[] message) throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return new InstructionMessage(MessageType.valueOf(message[INSTRUCTION_TYPE_POSITION]),
                    message[PRODUCT_CODE_POSITION],
                    parseInt(message[QUANTITY_POSITION]),
                    parseInt(message[UOM_POSITION]),
                    dateFormat.parse(message[TIMESTAMP_POSITION]));
        }
    }

}
