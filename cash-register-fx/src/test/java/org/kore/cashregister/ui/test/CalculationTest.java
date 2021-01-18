/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui.test;

import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kore.cashregister.ui.Calculation;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Konrad Renner
 */
@ExtendWith(MockitoExtension.class)
public class CalculationTest {

    @Mock
    StringProperty uiProperty;

    Calculation underTest;

    @BeforeEach
    public void setUp() {
        underTest = new Calculation(uiProperty);
    }

    @Test
    public void add_calculation_character() {
        when(uiProperty.get()).thenReturn("");
        underTest.addCalculationCharacter("5");
        verify(uiProperty).setValue("5");
    }

    @Test
    public void add_calculation_operation() {
        when(uiProperty.get()).thenReturn("");
        underTest.addCalculationCharacter("5");
        when(uiProperty.get()).thenReturn("5");
        underTest.addCalculationOperation("+");
        verify(uiProperty).setValue("5+");
    }

    @Test
    public void add_calculation_operation_no_number_set() {
        underTest.addCalculationOperation("+");
        verify(uiProperty, times(0)).setValue("+");
    }

    @Test
    public void add_calculation_last_number_char_is_comma() {
        when(uiProperty.get()).thenReturn("");
        underTest.addCalculationCharacter("1");
        when(uiProperty.get()).thenReturn("1");
        underTest.addCalculationCharacter(",");
        when(uiProperty.get()).thenReturn("1,");
        underTest.addCalculationOperation("+");
        //verify 2 times => 1. because of adding 1 and 2. because of "deletion" of ,
        verify(uiProperty, times(2)).setValue("1");
        verify(uiProperty).setValue("1+");
    }

    @Test
    public void removeLastChar_number() {
        when(uiProperty.get()).thenReturn("");
        underTest.addCalculationCharacter("1");
        when(uiProperty.get()).thenReturn("1");
        underTest.addCalculationCharacter("1");
        when(uiProperty.get()).thenReturn("11");
        verify(uiProperty).setValue("1");
        underTest.removeLastCharacter();
        verify(uiProperty, times(2)).setValue("1");
    }

    @Test
    public void removeLastChar_number_then_everything_is_empty() {
        when(uiProperty.get()).thenReturn("");
        underTest.addCalculationCharacter("1");
        when(uiProperty.get()).thenReturn("1");
        verify(uiProperty).setValue("1");
        underTest.removeLastCharacter();
        verify(uiProperty).setValue("");
    }

    @Test
    public void remove_last_char_everything_empty() {
        when(uiProperty.get()).thenReturn("");
        verifyNoMoreInteractions(uiProperty);
        underTest.removeLastCharacter();
    }

    @Test
    public void remove_last_char_operator() {
        when(uiProperty.get()).thenReturn("");
        underTest.addCalculationCharacter("1");
        when(uiProperty.get()).thenReturn("1");
        verify(uiProperty).setValue("1");
        underTest.addCalculationOperation("+");
        when(uiProperty.get()).thenReturn("1+");
        underTest.removeLastCharacter();
        verify(uiProperty, times(2)).setValue("1");
    }
}
