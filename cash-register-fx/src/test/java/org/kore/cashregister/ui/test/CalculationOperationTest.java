/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui.test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.kore.cashregister.ui.CalculationOperation;

/**
 *
 * @author Konrad Renner
 */
public class CalculationOperationTest {

    @Test
    public void testAdd() {
        BigDecimal result = CalculationOperation.evaluate('+').calculate(BigDecimal.ONE, BigDecimal.ONE);
        assertEquals(new BigDecimal("2").setScale(2), result);
    }

    @Test
    public void testSubtract() {
        BigDecimal result = CalculationOperation.evaluate('-').calculate(BigDecimal.ONE, BigDecimal.ONE);
        assertEquals(BigDecimal.ZERO.setScale(2), result);
    }

    @Test
    public void testDivide() {
        BigDecimal result = CalculationOperation.evaluate('/').calculate(BigDecimal.TEN, BigDecimal.TEN);
        assertEquals(BigDecimal.ONE.setScale(2), result);
    }

    @Test
    public void testMultiply() {
        BigDecimal result = CalculationOperation.evaluate('*').calculate(BigDecimal.TEN, BigDecimal.TEN);
        assertEquals(new BigDecimal("100").setScale(2), result);
    }
}
