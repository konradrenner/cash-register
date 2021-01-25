/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.ui;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.kore.cashregister.MenuEntry;
import org.kore.cashregister.MenuEntryCategory;

/**
 *
 * @author Konrad Renner
 */
public class MenuButtonFactory {

    private final MenuEntryCategory category;
    private final Set<MenuEntry> entries;

    public MenuButtonFactory(MenuEntryCategory category, Collection<MenuEntry> entries) {
        this.category = category;
        this.entries = new TreeSet<>();
        entries.stream().filter(entry -> entry.getCategory() == category).forEach(this.entries::add);
    }

    public void setButtonsOnPane(GridPane pane, Consumer<String> buttonClickEvent) {
        int column = 0;
        int row = 0;
        String cssClass = category.name().toLowerCase() + "_button";

        for (MenuEntry entry : entries) {
            Button button = new Button(entry.getDescription() + "\n € " + entry.getUnitPrice());
            button.setId(entry.getId());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent handler) {
                    Button button = (Button) handler.getSource();
                    buttonClickEvent.accept(button.getId());
                    button.getParent().requestFocus();
                }
            });
            button.getStyleClass().add(cssClass);

            pane.add(button, column, row);
            column++;
            if (column == 4) {
                row++;
                column = 0;
            }
        }
    }
}
