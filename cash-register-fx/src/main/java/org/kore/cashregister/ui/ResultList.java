/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.kore.cashregister.OrderEntry;

/**
 *
 * @author Konrad Renner
 */
public class ResultList {

    private final ObservableList<HBox> uiList;
    private final List<OrderEntry> entries;
    private BigDecimal actTotal;

    public ResultList(ObservableList<HBox> uiList) {
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
        Label descLabel = new Label(entry.getDescription());
        descLabel.setAlignment(Pos.CENTER_LEFT);
        descLabel.setPrefWidth(160);
        Label valueLabel = new Label(value.toPlainString());
        valueLabel.setAlignment(Pos.CENTER_RIGHT);
        valueLabel.setPrefWidth(80);
        HBox box = new HBox();
        box.getChildren().add(descLabel);
        box.getChildren().add(valueLabel);
        box.setPrefWidth(240);
        box.setAlignment(Pos.CENTER_RIGHT);
        uiList.add(box);
        actTotal = actTotal.add(value);
        return actTotal;
    }

    public BigDecimal removeEntry(int index) {
        OrderEntry entry = entries.remove(index);
        uiList.remove(index);
        actTotal = actTotal.subtract(entry.getUnitPrice().multiply(BigDecimal.valueOf(entry.getAmount())));
        return actTotal;
    }

    public List<OrderEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public BigDecimal getActTotal() {
        return actTotal;
    }

}
