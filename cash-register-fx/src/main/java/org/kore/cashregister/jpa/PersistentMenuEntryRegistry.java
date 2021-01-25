/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.jpa;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;
import org.kore.cashregister.MenuEntry;
import org.kore.cashregister.MenuEntryCategory;
import org.kore.cashregister.MenuEntryRegistry;

/**
 *
 * @author Konrad Renner
 */
public class PersistentMenuEntryRegistry implements MenuEntryRegistry {

    @Override
    public Set<MenuEntry> findByCategory(MenuEntryCategory value) {
        TreeSet<MenuEntry> entries = new TreeSet<>();

        if (value == MenuEntryCategory.BEVERAGE) {
            entries.add(new PersistentMenuEntry("Almdudler", BigDecimal.TEN, MenuEntryCategory.BEVERAGE));
            entries.add(new PersistentMenuEntry("Wasser", BigDecimal.ZERO, MenuEntryCategory.BEVERAGE));
            entries.add(new PersistentMenuEntry("Bier", BigDecimal.ONE, MenuEntryCategory.BEVERAGE));
        } else if (value == MenuEntryCategory.FOOD) {
            entries.add(new PersistentMenuEntry("Schnitzel", BigDecimal.TEN, MenuEntryCategory.FOOD));
            entries.add(new PersistentMenuEntry("Semmel", BigDecimal.ONE, MenuEntryCategory.FOOD));
        } else {
            entries.add(new PersistentMenuEntry("Rauchwaren", BigDecimal.TEN, MenuEntryCategory.OTHER));
        }
        return entries;
    }

    @Override
    public Set<MenuEntry> findAll() {
        TreeSet<MenuEntry> entries = new TreeSet<>();
        entries.add(new PersistentMenuEntry("Almdudler", BigDecimal.TEN, MenuEntryCategory.BEVERAGE));
        entries.add(new PersistentMenuEntry("Wasser", BigDecimal.ZERO, MenuEntryCategory.BEVERAGE));
        entries.add(new PersistentMenuEntry("Bier", BigDecimal.ONE, MenuEntryCategory.BEVERAGE));
        entries.add(new PersistentMenuEntry("Schnitzel", BigDecimal.TEN, MenuEntryCategory.FOOD));
        entries.add(new PersistentMenuEntry("Semmel", BigDecimal.ONE, MenuEntryCategory.FOOD));
        entries.add(new PersistentMenuEntry("Rauchwaren", BigDecimal.TEN, MenuEntryCategory.OTHER));
        return entries;
    }


}
