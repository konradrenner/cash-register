module org.kore.cashregister {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;

    opens org.kore.cashregister to javafx.fxml;
    exports org.kore.cashregister;
    exports org.kore.cashregister.ui;
}
