/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.print;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import org.kore.cashregister.OrderEntry;
import org.kore.cashregister.OrderPrinter;

/**
 *
 * @author Konrad Renner
 */
public class ReceiptPrinter implements OrderPrinter {

    @Override
    public void print(Instant orderTime, List<OrderEntry> entries, BigDecimal total) {
        System.out.println("Timestamp of Order:" + orderTime.atZone(ZoneId.systemDefault()));
        System.out.println("Total:" + total);
        System.out.println("Entries:" + entries);
    }

}
