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
import java.util.function.BiFunction;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Konrad Renner
 */
public class CalculationBuilder {

    private final StringProperty uiProperty;
    private final List<CalculationNumber> numbers;
    private final List<CalculationOperation> operations;
    private final BiFunction<List<BigDecimal>, List<CalculationOperation>, BigDecimal> calculationFunction;

    public CalculationBuilder(StringProperty binding, BiFunction<List<BigDecimal>, List<CalculationOperation>, BigDecimal> calculationFunction) {
        this.uiProperty = binding;
        this.numbers = new ArrayList<>();
        this.numbers.add(new CalculationNumber());
        this.operations = new ArrayList<>();
        this.calculationFunction = calculationFunction;
    }

    public void addCalculationCharacter(String value) {
        if (numbers.get(numbers.size() - 1).addCharacter(value.charAt(0))) {
            uiProperty.setValue(uiProperty.get() + value);
        }
    }

    public void addCalculationOperation(String value) {
        CalculationNumber actNumber = numbers.get(numbers.size() - 1);
        if (actNumber.isEmpty()) {
            return;
        }

        String prop = uiProperty.get();
        if (actNumber.isLastCharacterComma()) {
            numbers.get(numbers.size() - 1).removeLastCharacter();

            prop = prop.substring(0, prop.length() - 1);
            uiProperty.setValue(prop);
        }

        numbers.add(new CalculationNumber());
        this.operations.add(CalculationOperation.evaluate(value.charAt(0)));
        uiProperty.setValue(prop + value);
    }

    public void clear() {
        this.uiProperty.setValue("");
        this.numbers.clear();
        this.numbers.add(new CalculationNumber());
        this.operations.clear();
    }

    public void removeLastCharacter() {
        CalculationNumber number = numbers.get(numbers.size() - 1);
        if (number.isEmpty()) {
            removeOperatorAndEmptyNumber();
        } else {
            number.removeLastCharacter();
        }
        String prop = uiProperty.get();
        if (prop.isEmpty()) {
            return;
        }
        uiProperty.setValue(prop.substring(0, prop.length() - 1));
    }

    private void removeOperatorAndEmptyNumber() {
        if (numbers.size() > 1) {
            numbers.remove(numbers.size() - 1);
        }

        if (!this.operations.isEmpty()) {
            this.operations.remove(this.operations.size() - 1);
        }
    }

    public BigDecimal getResult() {
        if (numbers.size() == 1 && numbers.get(0).isEmpty()) {
            return BigDecimal.ZERO;
        }

        if (numbers.get(numbers.size() - 1).isLastCharacterComma()) {
            numbers.get(numbers.size() - 1).removeLastCharacter();
        }

        ArrayList<BigDecimal> mapped = new ArrayList<>(numbers.size());

        numbers.stream().filter(number -> !number.isEmpty()).map(number -> number.toBigDecimal()).forEach(mapped::add);

        return this.calculationFunction.apply(mapped, Collections.unmodifiableList(operations));
    }
}
