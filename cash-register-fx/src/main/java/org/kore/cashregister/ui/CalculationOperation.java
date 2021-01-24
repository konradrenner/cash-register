/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

/**
 *
 * @author Konrad Renner
 */
public enum CalculationOperation {

    ADD((calculatedValue, nextvalue) -> calculatedValue.add(nextvalue)),
    SUBTRACT((calculatedValue, nextvalue) -> calculatedValue.subtract(nextvalue)),
    DIVIDE((calculatedValue, nextvalue) -> calculatedValue.divide(nextvalue, 2, RoundingMode.HALF_UP)),
    MULTIPLY((calculatedValue, nextvalue) -> calculatedValue.multiply(nextvalue));

    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> function;

    private CalculationOperation(BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        this.function = function;
    }

    public BigDecimal calculate(BigDecimal oldValue, BigDecimal newValue) {
        return function.apply(oldValue, newValue).setScale(2, RoundingMode.HALF_UP);
    }

    public static CalculationOperation evaluate(char operation) {
        switch (operation) {
            case '-':
                return SUBTRACT;
            case '/':
                return DIVIDE;
            case '*':
                return MULTIPLY;
            default:
                return ADD;
        }
    }
}
