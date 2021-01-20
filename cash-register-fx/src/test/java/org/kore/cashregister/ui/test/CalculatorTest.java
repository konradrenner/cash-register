/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kore.cashregister.ui.CalculationOperation;
import org.kore.cashregister.ui.Calculator;

/**
 *
 * @author Konrad Renner
 */
public class CalculatorTest {

    private Calculator underTest;

    @BeforeEach
    public void setUp() {
        underTest = new Calculator();
    }

    @Test
    public void wrong_argument_constellation() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.ONE);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.ADD);

        assertThrows(IllegalArgumentException.class, () -> underTest.calculate(numbers, operations));
    }

    @Test
    public void empty_numbers() {
        List<BigDecimal> numbers = new ArrayList<>();
        List<CalculationOperation> operations = new ArrayList<>();

        assertEquals(BigDecimal.ZERO.setScale(2), underTest.calculate(numbers, operations));
    }

    @Test
    public void empty_operations() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.ONE);
        List<CalculationOperation> operations = new ArrayList<>();

        assertEquals(BigDecimal.ONE.setScale(2), underTest.calculate(numbers, operations));

    }

    @Test
    public void one_number_and_one_operation() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.ONE);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.ADD);

        assertEquals(BigDecimal.ONE.setScale(2), underTest.calculate(numbers, operations));

    }

    @Test
    public void just_addition_and_subtraction() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.ONE);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.SUBTRACT);

        BigDecimal calculated = underTest.calculate(numbers, operations);
        assertEquals(BigDecimal.ONE.setScale(2), calculated);
    }

    @Test
    public void just_multiplication_and_division() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.TEN);
        numbers.add(BigDecimal.TEN);
        numbers.add(BigDecimal.TEN);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.DIVIDE);

        BigDecimal calculated = underTest.calculate(numbers, operations);
        assertEquals(BigDecimal.TEN.setScale(2), calculated);
    }

    @Test
    public void everything_mixed_finish_add() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.TEN);
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.TEN);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.DIVIDE);
        operations.add(CalculationOperation.SUBTRACT);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.ADD);

        BigDecimal calculated = underTest.calculate(numbers, operations);
        assertEquals(BigDecimal.valueOf(26).setScale(2), calculated);
    }

    @Test
    public void everything_mixed_finish_multiply() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.TEN);
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.TEN);
        numbers.add(BigDecimal.ONE);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.DIVIDE);
        operations.add(CalculationOperation.SUBTRACT);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.MULTIPLY);

        BigDecimal calculated = underTest.calculate(numbers, operations);
        assertEquals(BigDecimal.valueOf(26).setScale(2), calculated);
    }

    @Test
    public void everything_mixed_finish_add_last_part_is_operation() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.TEN);
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.TEN);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.DIVIDE);
        operations.add(CalculationOperation.SUBTRACT);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.ADD);

        BigDecimal calculated = underTest.calculate(numbers, operations);
        assertEquals(BigDecimal.valueOf(26).setScale(2), calculated);
    }

    @Test
    public void everything_mixed_finish_multiply_last_part_is_operation() {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(BigDecimal.TEN);
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(4));
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.ONE);
        numbers.add(BigDecimal.valueOf(2));
        numbers.add(BigDecimal.TEN);
        List<CalculationOperation> operations = new ArrayList<>();
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.DIVIDE);
        operations.add(CalculationOperation.SUBTRACT);
        operations.add(CalculationOperation.MULTIPLY);
        operations.add(CalculationOperation.ADD);
        operations.add(CalculationOperation.MULTIPLY);

        BigDecimal calculated = underTest.calculate(numbers, operations);
        assertEquals(BigDecimal.valueOf(26).setScale(2), calculated);
    }
}
