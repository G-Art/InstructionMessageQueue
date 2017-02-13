package com.epam.data;

import com.epam.queue.MessageType;

import java.util.Date;
import java.util.Optional;

public class InstructionMessage {

    private Optional<MessageType> instructionType;
    private String productCode;
    private int quantity;
    private int uom;
    private Date timestamp;


    public InstructionMessage() {
    }

    public MessageType getInstructionType() {
        return instructionType.orElseThrow(NullPointerException::new);
    }

    public void setInstructionType(MessageType instructionType) {
        this.instructionType = Optional.of(instructionType);
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUom() {
        return uom;
    }

    public void setUom(int uom) {
        this.uom = uom;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
