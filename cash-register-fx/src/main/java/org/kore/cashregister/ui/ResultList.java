/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final LinkedHashMap<String, InternalEntry> entries;
    private BigDecimal actTotal;

    public ResultList(ObservableList<HBox> uiList) {
        this.uiList = uiList;
        this.entries = new LinkedHashMap<>();
        this.actTotal = BigDecimal.ZERO.setScale(2);
    }

    public BigDecimal addEntry(OrderEntry entry) {
        BigDecimal value = entry.getUnitPrice().multiply(BigDecimal.valueOf(entry.getAmount()));

        if (value.doubleValue() == 0.0) {
            return BigDecimal.ZERO;
        }

        InternalEntry oldEntry = entries.get(entry.getDescription());
        if (oldEntry == null) {
            oldEntry = new InternalEntry(uiList.size(), entry);
            entries.put(entry.getDescription(), oldEntry);
            Label descLabel = new Label(entry.getDescription());
            descLabel.setAlignment(Pos.CENTER_LEFT);
            descLabel.setPrefWidth(160);
            Label valueLabel = new Label(value.toPlainString());
            valueLabel.setAlignment(Pos.CENTER_RIGHT);
            valueLabel.setPrefWidth(80);
            HBox box = new HBox();
            box.setId(entry.getDescription());
            box.getChildren().add(descLabel);
            box.getChildren().add(valueLabel);
            box.setPrefWidth(240);
            box.setAlignment(Pos.CENTER_RIGHT);
            uiList.add(box);
        } else {
            OrderEntry updateingEntry = oldEntry.entry();
            int index = oldEntry.index();
            updateingEntry.increaseAmount();
            entries.put(entry.getDescription(), new InternalEntry(index, updateingEntry));

            updateEntryText(index, updateingEntry);
        }

        actTotal = actTotal.add(value);
        return actTotal;
    }

    private void updateEntryText(int index, OrderEntry updateingEntry) {
        HBox box = uiList.get(index);
        Label descLabel = (Label) box.getChildren().get(0);
        descLabel.setText(updateingEntry.getAmount() + " x " + updateingEntry.getDescription());
        Label valueLabel = (Label) box.getChildren().get(1);
        valueLabel.setText(updateingEntry.getUnitPrice().multiply(BigDecimal.valueOf(updateingEntry.getAmount())).toPlainString());
    }

    public BigDecimal removeEntry(int index) {
        findEntryByIndex(index).ifPresent(entry -> {
            OrderEntry toRemove = entries.remove(entry.getDescription()).entry();
            uiList.remove(index);
            actTotal = actTotal.subtract(toRemove.getUnitPrice().multiply(BigDecimal.valueOf(toRemove.getAmount())));
            decreaseUIIndizes(index + 1);
        });

        return actTotal;
    }

    public BigDecimal removeEntries(Set<String> desciptions) {
        desciptions.stream().forEach(description -> {
            InternalEntry toRemove = entries.remove(description);
            OrderEntry entry = toRemove.entry();
            int index = toRemove.index();
            uiList.remove(index);
            actTotal = actTotal.subtract(entry.getUnitPrice().multiply(BigDecimal.valueOf(entry.getAmount())));
            decreaseUIIndizes(index + 1);
        });
        return actTotal;
    }

    Optional<OrderEntry> findEntryByIndex(final int index) {
        return entries.values().stream()
                .filter(entry -> entry.index() == index)
                .map(entry -> entry.entry())
                .findFirst();
    }

    public BigDecimal decreaseEntry(int index) {

        findEntryByIndex(index).ifPresent(entry -> {
            int oldAmount = entry.getAmount();
            entry.decreaseAmount();
            actTotal = actTotal.subtract(entry.getUnitPrice());
            // use old amount, because decreasing has no relevance for ManualOrderEntry
            if (oldAmount == 1) {
                uiList.remove(index);
                entries.remove(entry.getDescription());
                decreaseUIIndizes(index + 1);
            } else {
                updateEntryText(index, entry);
            }
        });
        return actTotal;
    }

    void decreaseUIIndizes(int startWith) {
        entries.values().stream()
                .filter(internal -> internal.index() >= startWith)
                .forEach(internal -> {
            int newIndex = internal.index - 1;
            OrderEntry entry = internal.entry();
            entries.put(entry.getDescription(), new InternalEntry(newIndex, entry));
                });
    }

    public List<OrderEntry> getEntries() {
        Collection<InternalEntry> values = entries.values();
        List<OrderEntry> ret = new ArrayList<>(values.size());
        values.stream().map(internal -> internal.entry()).forEach(ret::add);
        return ret;
    }

    public BigDecimal getActTotal() {
        return actTotal;
    }

    public void clear() {
        entries.clear();
        uiList.clear();
        actTotal = new BigDecimal(BigInteger.ZERO).setScale(2);
    }

    private static record InternalEntry(int index, OrderEntry entry) {

    }
}
