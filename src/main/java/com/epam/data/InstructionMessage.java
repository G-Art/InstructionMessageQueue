package com.epam.data;

import java.util.Date;

public class InstructionMessage {

    private String instructionType;
    private String productCode;
    private int    quantity;
    private int    uom;
    private Date   timestamp;


    public InstructionMessage() {
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
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
