/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

import java.math.BigDecimal;

/**
 *
 * @author Konrad Renner
 */
public class ManualOrderEntry implements OrderEntry {

    private final static int ONE = 1;

    private final BigDecimal unitPrice;
    private final String description;

    public ManualOrderEntry(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        this.description = "";
    }

    public ManualOrderEntry(BigDecimal unitPrice, String description) {
        this.unitPrice = unitPrice;
        this.description = description;
    }


    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getAmount() {
        return ONE;
    }

    @Override
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    @Override
    public String toString() {
        return "ManualOrderEntry{" + "unitPrice=" + unitPrice + ", description=" + description + '}';
    }

}
