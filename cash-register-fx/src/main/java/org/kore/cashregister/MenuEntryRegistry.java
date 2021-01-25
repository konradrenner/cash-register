/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

import java.util.Set;

/**
 *
 * @author Konrad Renner
 */
public interface MenuEntryRegistry {

    Set<MenuEntry> findByCategory(MenuEntryCategory value);

    Set<MenuEntry> findAll();
}
