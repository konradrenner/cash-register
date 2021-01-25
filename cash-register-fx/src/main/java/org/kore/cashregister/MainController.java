/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.kore.cashregister.jpa.PersistentMenuEntryRegistry;
import org.kore.cashregister.ui.CalculationBuilder;
import org.kore.cashregister.ui.Calculator;
import org.kore.cashregister.ui.MenuButtonFactory;
import org.kore.cashregister.ui.ResultList;

/**
 * FXML Controller class
 *
 * @author Konrad Renner
 */
public class MainController implements Initializable {

    private static List<KeyCode> operationKeys = Arrays.asList(KeyCode.PLUS, KeyCode.ADD, KeyCode.SUBTRACT, KeyCode.MINUS, KeyCode.SLASH, KeyCode.MULTIPLY, KeyCode.DIVIDE);
    private static List<KeyCode> numpadKeys = Arrays.asList(KeyCode.DIGIT0, KeyCode.NUMPAD0,
            KeyCode.DIGIT1, KeyCode.NUMPAD1,
            KeyCode.DIGIT2, KeyCode.NUMPAD2,
            KeyCode.DIGIT3, KeyCode.NUMPAD3,
            KeyCode.DIGIT4, KeyCode.NUMPAD4,
            KeyCode.DIGIT5, KeyCode.NUMPAD5,
            KeyCode.DIGIT6, KeyCode.NUMPAD6,
            KeyCode.DIGIT7, KeyCode.NUMPAD7,
            KeyCode.DIGIT8, KeyCode.NUMPAD8,
            KeyCode.DIGIT9, KeyCode.NUMPAD9);

    @FXML
    ListView resultList;

    @FXML
    TextField resultOverall;

    @FXML
    TextField calculatorField;

    @FXML
    BorderPane mainPane;

    @FXML
    GridPane beveragePane;

    @FXML
    GridPane foodPane;

    @FXML
    GridPane otherPane;

    private CalculationBuilder manualCalculation;
    private Calculator calculator;
    private ResultList results;
    private MenuEntryRegistry menuRegistry;
    private Map<String, MenuEntry> menuEntries;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        results = new ResultList(resultList.getItems());
        menuRegistry = new PersistentMenuEntryRegistry();
        initTabs();

        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (KeyCode.ENTER.equals(keyEvent.getCode())) {
                    processCalculation();
                } else if (KeyCode.BACK_SPACE.equals(keyEvent.getCode())) {
                    processCE();
                } else if (KeyCode.DELETE.equals(keyEvent.getCode())) {
                    processC();
                } else if (KeyCode.COMMA.equals(keyEvent.getCode()) || KeyCode.SEPARATOR.equals(keyEvent.getCode())) {
                    processNumpad(keyEvent.getText());
                } else if (operationKeys.contains(keyEvent.getCode())) {
                    processOperation(keyEvent.getText());
                } else if (numpadKeys.contains(keyEvent.getCode())) {
                    processNumpad(keyEvent.getText());
                }
            }
        });

        calculator = new Calculator();
        manualCalculation = new CalculationBuilder(calculatorField.textProperty(), calculator::calculate);
    }

    void initTabs() {
        menuEntries = new HashMap<>();
        menuRegistry.findAll().stream().forEach(entry -> menuEntries.put(entry.getId(), entry));

        initOneTab(MenuEntryCategory.FOOD, menuEntries, foodPane);
        initOneTab(MenuEntryCategory.BEVERAGE, menuEntries, beveragePane);
        initOneTab(MenuEntryCategory.OTHER, menuEntries, otherPane);
    }

    void initOneTab(MenuEntryCategory category, Map<String, MenuEntry> entries, GridPane pane) {
        MenuButtonFactory buttonFactory = new MenuButtonFactory(category, entries.values());
        buttonFactory.setButtonsOnPane(pane, this::menuButtonClicked);
    }

    public void menuButtonClicked(String id) {
        MenuEntry entry = this.menuEntries.get(id);
        PredefinedOrderEntry order = new PredefinedOrderEntry(entry.getDescription(), entry.getUnitPrice());
        BigDecimal newTotal = results.addEntry(order);
        if (newTotal.longValue() > 0) {
            resultOverall.setText(newTotal.toPlainString());
        }
    }

    @FXML
    public void numberButtonClicked(ActionEvent e) {
        processNumpad(((Button) e.getSource()).getText());
    }

    void processNumpad(String numpad) {
        manualCalculation.addCalculationCharacter(numpad);
    }

    @FXML
    public void operationButtonClicked(ActionEvent e) {
        processOperation(((Button) e.getSource()).getText());
    }

    void processOperation(String operation) {
        manualCalculation.addCalculationOperation(operation);
    }

    @FXML
    public void ceButtonClicked(ActionEvent e) {
        processCE();
    }

    void processCE() {
        manualCalculation.removeLastCharacter();
    }

    @FXML
    public void cButtonClicked(ActionEvent e) {
        processC();
    }

    void processC() {
        manualCalculation.clear();
    }


    @FXML
    public void calculateButtonClicked(ActionEvent e) {
        processCalculation();
    }

    void processCalculation() {
        ManualOrderEntry newEntry = new ManualOrderEntry(manualCalculation.getResult());
        BigDecimal newTotal = results.addEntry(newEntry);
        if (newTotal.longValue() > 0) {
            resultOverall.setText(newTotal.toPlainString());
        }
        manualCalculation.clear();
    }

    @FXML
    public void checkoutButtonClicked(ActionEvent e) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Bezahlung");
        alert.setHeaderText("Zahlvorgang ist gestartet");
        alert.setContentText("Bitte folgenden Betrag bezahlen: € " + resultOverall.getText());

        alert.showAndWait();
    }

    @FXML
    public void abortButtonClicked(ActionEvent e) {
        ObservableList<Integer> selectedIndices = resultList.getSelectionModel().getSelectedIndices();
        if (selectedIndices.size() > 0) {
            BigDecimal newTotal = BigDecimal.ZERO;
            for (Integer index : selectedIndices) {
                newTotal = results.removeEntry(index.intValue());
            }
            resultOverall.setText(newTotal.toPlainString());
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Bitte eine Auswahl in der Liste treffen");
//alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();
        }
    }
}
