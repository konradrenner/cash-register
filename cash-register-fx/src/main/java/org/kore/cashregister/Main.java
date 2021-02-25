/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister;

/**
 * This class is just needed because of:
 * https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
 *
 * @author Konrad Renner
 */
public class Main {
    public static void main(String[] args) {
        App.main(args);
    }
}
