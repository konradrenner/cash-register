/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.kore.cashregister.ui.CalculationBuilder;
import org.kore.cashregister.ui.Calculator;

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

    private CalculationBuilder manualCalculation;
    private Calculator calculator;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        System.out.println("Result=" + manualCalculation.getResult());
    }

    @FXML
    public void checkoutButtonClicked(ActionEvent e) {

    }

    @FXML
    public void abortButtonClicked(ActionEvent e) {

    }
}
