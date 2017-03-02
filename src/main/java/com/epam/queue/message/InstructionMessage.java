package com.epam.queue.message;

import java.text.ParseException;
import java.util.Date;

public final class InstructionMessage {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final MessageType instructionType;
    private final String productCode;
    private final int quantity;
    private final int uom;
    private final Date timestamp;


    public InstructionMessage(MessageType messageType, String productCode, int quantity, int uom, Date timestamp) {
        this.instructionType =  messageType;
        this.productCode = productCode;
        this.quantity = quantity;
        this.uom = uom;
        this.timestamp = new Date(timestamp.getTime());
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
        return new Date(timestamp.getTime());
    }


}
