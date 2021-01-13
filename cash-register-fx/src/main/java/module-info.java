module org.kore.cashregister {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.kore.cashregister to javafx.fxml;
    exports org.kore.cashregister;
}
