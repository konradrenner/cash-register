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

    private final BigDecimal unitPrice;
    private final String description;
    private int amount;

    public PredefinedOrderEntry(String description, BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        this.description = description;
        this.amount = 1;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void increaseAmount() {
        amount++;
    }

    @Override
    public void decreaseAmount() {
        if (amount == 0) {
            return;
        }
        amount--;
    }


    @Override
    public BigDecimal getUnitPrice() {
        return this.unitPrice.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "PredefinedOrderEntry{" + "unitPrice=" + unitPrice + ", description=" + description + ", amount=" + amount + '}';
    }

}
