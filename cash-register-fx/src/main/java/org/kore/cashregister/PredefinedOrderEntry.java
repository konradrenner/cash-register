/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Konrad Renner
 */
public class PredefinedOrderEntry implements OrderEntry {

    private final static int ONE = 1;

    private final BigDecimal unitPrice;
    private final String description;

    public PredefinedOrderEntry(String description, BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getAmount() {
        return ONE;
    }

    @Override
    public BigDecimal getUnitPrice() {
        return this.unitPrice.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "PredefinedOrderEntry{" + "unitPrice=" + unitPrice + ", description=" + description + '}';
    }

}
