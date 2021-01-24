/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import org.kore.cashregister.OrderEntry;

/**
 *
 * @author Konrad Renner
 */
public class ResultList {

    private final ObservableList<String> uiList;
    private final List<OrderEntry> entries;
    private BigDecimal actTotal;

    public ResultList(ObservableList<String> uiList) {
        this.uiList = uiList;
        this.entries = new ArrayList<>();
        this.actTotal = BigDecimal.ZERO.setScale(2);
    }

    public BigDecimal addEntry(OrderEntry entry) {
        BigDecimal value = entry.getUnitPrice().multiply(BigDecimal.valueOf(entry.getAmount()));

        if (value.longValue() == 0) {
            return BigDecimal.ZERO;
        }

        entries.add(entry);
        StringBuilder sb = new StringBuilder();
        if (entry.getAmount() > 1) {
            sb.append(entry.getAmount());
            sb.append("x ");
        }
        sb.append(entry.getDescription());
        sb.append(' ');
        sb.append(value);
        uiList.add(sb.toString());
        actTotal = actTotal.add(value);
        return actTotal;
    }

    public BigDecimal removeEntry(int index) {
        OrderEntry entry = entries.remove(index);
        actTotal = actTotal.subtract(entry.getUnitPrice().multiply(BigDecimal.valueOf(entry.getAmount())));
        return actTotal;
    }
}
