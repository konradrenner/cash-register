/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Konrad Renner
 */
public class CalculationNumber {

    private final List<Character> parts;
    private boolean containsComma;

    public CalculationNumber() {
        this.parts = new ArrayList<>();
    }

    public boolean addCharacter(char c) {
        if (c == ',' || c == '.') {
            if (containsComma) {
                return false;
            }
            if (parts.isEmpty()) {
                parts.add('0');
            }
            parts.add('.');
            containsComma = true;
            return true;
        }
        parts.add(c);
        return true;
    }

    boolean isEmpty() {
        return this.parts.isEmpty();
    }

    public void removeLastCharacter() {
        parts.remove(parts.size() - 1);
    }

    boolean isLastCharacterComma() {
        return parts.get(parts.size() - 1).charValue() == '.';
    }

    public BigDecimal toBigDecimal() {
        if (this.parts.get(this.parts.size() - 1) == '.') {
            this.parts.remove(this.parts.size() - 1);
        }
        StringBuilder sb = new StringBuilder();
        this.parts.stream().forEach(sb::append);
        return new BigDecimal(sb.toString()).setScale(2, RoundingMode.HALF_UP);
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        this.parts.stream().forEach(sb::append);
        return sb.toString();
    }
}
