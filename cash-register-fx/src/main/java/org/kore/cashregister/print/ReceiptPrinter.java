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
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
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

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            // Show the printer job status
//            jobStatus.textProperty().bind(job.jobStatusProperty().asString());

            // Print the node
            boolean printed = job.printPage(new Label("Hallo Welt"));

            if (printed) {
                // End the printer job
                job.endJob();
            } else {
                // Write Error Message
//                jobStatus.textProperty().unbind();
//                jobStatus.setText("Printing failed.");
            }
        }
    }

}
