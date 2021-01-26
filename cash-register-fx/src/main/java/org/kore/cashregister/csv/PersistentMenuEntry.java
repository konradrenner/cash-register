/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.csv;

import java.math.BigDecimal;
import java.util.Objects;
import org.kore.cashregister.MenuEntry;
import org.kore.cashregister.MenuEntryCategory;

/**
 *
 * @author Konrad Renner
 */
public class PersistentMenuEntry implements MenuEntry {

    private String description;
    private BigDecimal unitPrice;
    private MenuEntryCategory category;

    public PersistentMenuEntry(String description, BigDecimal unitPrice, MenuEntryCategory category) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    @Override
    public MenuEntryCategory getCategory() {
        return category;
    }

    @Override
    public String getId() {
        return category.name().toLowerCase() + "_" + description;
    }


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersistentMenuEntry other = (PersistentMenuEntry) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(MenuEntry o) {
        if (this.equals(o)) {
            return 0;
        }

        return this.description.compareTo(o.getDescription());
    }

    @Override
    public String toString() {
        return "PersistentMenuEntry{" + "description=" + description + ", unitPrice=" + unitPrice + ", category=" + category + '}';
    }

}
