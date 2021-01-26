/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.kore.cashregister.OrderEntry;
import org.kore.cashregister.OrderRegistry;

/**
 *
 * @author Konrad Renner
 */
public class PersistentOrderRegistry implements OrderRegistry {

    private final Path storageFolder;
    private final String fileId;

    public PersistentOrderRegistry(Path storageFolder) {
        this.storageFolder = storageFolder;
        this.fileId = Instant.now().toString() + ".csv";
    }



    @Override
    public void persistOrder(Instant orderTime, List<OrderEntry> entries) {
        Path orderFile = Paths.get(storageFolder.toString(), fileId);

        List<String> lines = new ArrayList<>(entries.size());

        entries.stream()
                .map(this::createLine)
                .map(line -> orderTime.atZone(ZoneId.systemDefault()).toString() + ';' + line)
                .forEach(lines::add);

        try {
            Files.write(orderFile, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    String createLine(OrderEntry entry) {
        StringBuilder sb = new StringBuilder(entry.getDescription());
        sb.append(';');
        sb.append(entry.getAmount());
        sb.append(';');
        sb.append(entry.getUnitPrice());

        return sb.toString();
    }

}
