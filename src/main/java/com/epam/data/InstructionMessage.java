package com.epam.data;

import java.util.Date;

public class InstructionMessage {

    private String instructionType;
    private String productCode;
    private int    quantity;
    private int    uom;
    private Date   timathtamp;

    public InstructionMessage(String instructionType, String productCode, int quantity, int uom, Date timathtamp) {
        this.instructionType = instructionType;
        this.productCode = productCode;
        this.quantity = quantity;
        this.uom = uom;
        this.timathtamp = timathtamp;
    }

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

    public Date getTimathtamp() {
        return timathtamp;
    }

    public void setTimathtamp(Date timathtamp) {
        this.timathtamp = timathtamp;
    }

}
