package com.epam.queue.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    public InstructionMessage(String[] message) throws ParseException {
        this.instructionType =  MessageType.valueOf(message[INSTRUCTION_TYPE_POSITION]);
        this.productCode = message[PRODUCT_CODE_POSITION];
        this.quantity = Integer.parseInt(message[QUANTITY_POSITION]);
        this.uom = Integer.parseInt(message[UOM_POSITION]);
        this.timestamp = new SimpleDateFormat(DATE_FORMAT).parse(message[TIMESTAMP_POSITION]);
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

}
