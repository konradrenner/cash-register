/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.csv;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import org.kore.cashregister.OrderEntry;
import org.kore.cashregister.OrderRegistry;

/**
 *
 * @author Konrad Renner
 */
public class PersistentOrderRegistry implements OrderRegistry {

    private static final String SEMICOLON = ";";

    private final Path storageFolder;
    private final String fileId;

    public PersistentOrderRegistry(Path storageFolder) {
        this.storageFolder = storageFolder;
        this.fileId = "belege.csv";
    }



    @Override
    public void persistOrder(String cashier, Instant orderTime, List<OrderEntry> entries) {
        Path orderFile = Paths.get(storageFolder.toString(), cashier + "_" + fileId);

        List<String> lines = new ArrayList<>(entries.size());
        if (Files.notExists(orderFile)) {
            lines.add(createHeader());
        }
        entries.stream()
                .map(this::createLine)
                .map(line -> orderTime.getEpochSecond() + SEMICOLON + orderTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + SEMICOLON + orderTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)) + SEMICOLON + line)
                .forEach(lines::add);

        try {
            Files.write(orderFile, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    String createLine(OrderEntry entry) {
        StringBuilder sb = new StringBuilder(entry.getDescription());
        sb.append(SEMICOLON);
        sb.append(entry.getUnitPrice());
        sb.append(SEMICOLON);
        sb.append(entry.getAmount());
        sb.append(SEMICOLON);
        sb.append(entry.getUnitPrice().multiply(BigDecimal.valueOf(entry.getAmount())));
        sb.append(SEMICOLON);


        return sb.toString();
    }

    String createHeader() {
        return "Bon-Nr.;Bon-Datum;Bon-Uhrzeit;Artikel;Einzelpreis;Menge;Preis;";
    }

}
