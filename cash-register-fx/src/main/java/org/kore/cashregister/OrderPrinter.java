/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 *
 * @author Konrad Renner
 */
public interface OrderPrinter {

    void print(Instant orderTime, List<OrderEntry> entries, BigDecimal total);
}
