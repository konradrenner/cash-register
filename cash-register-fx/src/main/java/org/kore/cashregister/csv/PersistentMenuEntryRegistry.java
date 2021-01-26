/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.csv;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.kore.cashregister.MenuEntry;
import org.kore.cashregister.MenuEntryCategory;
import org.kore.cashregister.MenuEntryRegistry;

/**
 *
 * @author Konrad Renner
 */
public class PersistentMenuEntryRegistry implements MenuEntryRegistry {

    private final Path storageFolder;

    public PersistentMenuEntryRegistry(Path storageFolder) {
        this.storageFolder = storageFolder;
    }

    @Override
    public Set<MenuEntry> findAll() {
        Path csv = Paths.get(storageFolder.toString(), "menu_entries.csv");
        TreeSet<MenuEntry> entries = new TreeSet<>();
        if (Files.exists(csv)) {
            try ( Stream<String> lines = Files.lines(csv)) {
                lines.map(this::lineToEntry)
                        .filter(optional -> optional.isPresent())
                        .map(optional -> optional.get())
                        .forEach(entries::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Didn't find menu_entries.csv at:" + csv);
        }
        return entries;
    }

    Optional<MenuEntry> lineToEntry(String line) {
        String[] splitted = line.split(";");
        if (splitted.length < 3) {
            return Optional.empty();
        }

        return Optional.of(new PersistentMenuEntry(splitted[1],
                new BigDecimal(splitted[2]).setScale(2, RoundingMode.HALF_UP),
                MenuEntryCategory.valueOf(splitted[0])));
    }
}
