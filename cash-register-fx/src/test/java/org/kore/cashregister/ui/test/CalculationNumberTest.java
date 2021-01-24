/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui.test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.kore.cashregister.ui.CalculationNumber;

/**
 *
 * @author Konrad Renner
 */
public class CalculationNumberTest {

    @Test
    public void addNumber() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        assertEquals("5", number.asString());
    }

    @Test
    public void addSomeNumbers() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('4');
        number.addCharacter('2');
        assertEquals("42", number.asString());
    }

    @Test
    public void addComma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        number.addCharacter(',');
        assertEquals("5.", number.asString());
    }

    @Test
    public void addComma_too_many_comma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        number.addCharacter(',');
        number.addCharacter(',');
        number.addCharacter('.');
        number.addCharacter('.');
        assertEquals("5.", number.asString());
    }

    @Test
    public void addComma_on_Beginning() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('.');
        assertEquals("0.", number.asString());
    }

    @Test
    public void removeLastCharacter() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        number.addCharacter('1');
        assertEquals("51", number.asString());
        number.removeLastCharacter();
        assertEquals("5", number.asString());
    }

    @Test
    public void toBigDecimal_last_char_is_comma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        number.addCharacter('.');
        assertEquals(new BigDecimal("5.00"), number.toBigDecimal());
    }

    @Test
    public void toBigDecimal_with_comma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        number.addCharacter('.');
        number.addCharacter('5');
        assertEquals(new BigDecimal("5.50"), number.toBigDecimal());
    }

    @Test
    public void toBigDecimal_with_comma_and_rounding() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        number.addCharacter('.');
        number.addCharacter('5');
        number.addCharacter('5');
        number.addCharacter('5');
        assertEquals(new BigDecimal("5.56"), number.toBigDecimal());
    }

    @Test
    public void toBigDecimal_without_comma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('5');
        number.addCharacter('5');
        assertEquals(new BigDecimal("55.00"), number.toBigDecimal());
    }

    public void noComma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('1');
        number.addCharacter('5');

        assertFalse(number.isLastCharacterComma());
    }

    public void one_after_Comma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('2');
        number.addCharacter(',');
        number.addCharacter('3');

        assertFalse(number.isLastCharacterComma());
    }

    public void some_after_Comma() {
        CalculationNumber number = new CalculationNumber();
        number.addCharacter('3');
        number.addCharacter('5');
        number.addCharacter(',');
        number.addCharacter('5');
        number.addCharacter('2');

        assertFalse(number.isLastCharacterComma());
    }

    public void number_empty_comma_test() {
        CalculationNumber number = new CalculationNumber();

        assertFalse(number.isLastCharacterComma());
    }
}
