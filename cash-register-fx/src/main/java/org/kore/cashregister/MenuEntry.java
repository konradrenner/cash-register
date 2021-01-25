/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

import java.math.BigDecimal;

/**
 *
 * @author Konrad Renner
 */
public interface MenuEntry extends Comparable<MenuEntry> {

    String getId();

    MenuEntryCategory getCategory();

    String getDescription();

    BigDecimal getUnitPrice();
}
