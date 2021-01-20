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
public class Calculator {

    public BigDecimal calculate(List<BigDecimal> numbers, List<CalculationOperation> operations) {
        checkArguments(numbers, operations);

        if (numbers.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        List<CalculationPart> parts = createParts(numbers, operations);

        BigDecimal result = parts.get(0).calculate(BigDecimal.ZERO).setScale(2);
        for (int i = 1; i < parts.size(); i++) {
            result = parts.get(i).calculate(result);
        }
        return result;
    }

    private void checkArguments(List<BigDecimal> numbers, List<CalculationOperation> operations) throws IllegalArgumentException {
        if (numbers.size() - 1 > operations.size()) {
            throw new IllegalArgumentException("wrong number of numbers and operations");
        }
    }

    List<CalculationPart> createParts(List<BigDecimal> numbers, List<CalculationOperation> operations) {
        List<CalculationPart> parts = new ArrayList<>();
        parts.add(new FirstPart(numbers.get(0)));
        CalculationPart actPart = null;
        for (int i = 1; i < numbers.size(); i++) {
            if (isStartOfGroupedCalculationPart(i, operations)) {
                actPart = new GroupedCalculationPart();
                actPart.add(numbers.get(i), operations.get(i - 1));
                parts.add(actPart);
            } else if (isNewSingleCalculationPart(i, operations)) {
                actPart = new SingleNumberPart();
                actPart.add(numbers.get(i), operations.get(i - 1));
                parts.add(actPart);
            } else if (actPart instanceof GroupedCalculationPart) {
                actPart.add(numbers.get(i), operations.get(i - 1));
            } else {
                actPart = new SingleNumberPart();
                actPart.add(numbers.get(i), operations.get(i - 1));
                parts.add(actPart);
            }
        }

        return parts;
    }

    boolean isNewSingleCalculationPart(int i, List<CalculationOperation> operations) {
        if (operations.get(i - 1) == CalculationOperation.ADD || operations.get(i - 1) == CalculationOperation.SUBTRACT) {
            return true;
        }
        return false;
    }

    boolean isStartOfGroupedCalculationPart(int i, List<CalculationOperation> operations) {
        if (operations.size() <= i) {
            return false;
        }

        if (operations.get(i - 1) == CalculationOperation.ADD || operations.get(i - 1) == CalculationOperation.SUBTRACT) {
            if (operations.get(i) == CalculationOperation.DIVIDE || operations.get(i) == CalculationOperation.MULTIPLY) {
                return true;
            }
        }
        return false;
    }

    private static interface CalculationPart {

        void add(BigDecimal number, CalculationOperation operation);

        BigDecimal calculate(BigDecimal previousNumber);
    }

    private static class SingleNumberPart implements CalculationPart {

        private BigDecimal number;
        private CalculationOperation operation;

        @Override
        public void add(BigDecimal number, CalculationOperation operation) {
            this.number = number;
            this.operation = operation;
        }

        @Override
        public BigDecimal calculate(BigDecimal previousNumber) {
            return operation.calculate(previousNumber, number);
        }

    }

    private static class FirstPart implements CalculationPart {

        private BigDecimal number;

        public FirstPart(BigDecimal number) {
            this.number = number;
        }

        @Override
        public void add(BigDecimal number, CalculationOperation operation) {
            this.number = number;
        }

        @Override
        public BigDecimal calculate(BigDecimal previousNumber) {
            //nothing todo
            return this.number;
        }

    }

    private static class GroupedCalculationPart implements CalculationPart {

        private List<BigDecimal> number;
        private List<CalculationOperation> operation;

        public GroupedCalculationPart() {
            this.number = new ArrayList<>();
            this.operation = new ArrayList<>();
        }


        @Override
        public void add(BigDecimal number, CalculationOperation operation) {
            this.number.add(number);
            this.operation.add(operation);
        }

        @Override
        public BigDecimal calculate(BigDecimal previousNumber) {
            BigDecimal snapshot = number.get(0);
            for (int i = 1; i < number.size(); i++) {
                snapshot = operation.get(i).calculate(snapshot, number.get(i));
            }
            return operation.get(0).calculate(previousNumber, snapshot);
        }

    }
}
