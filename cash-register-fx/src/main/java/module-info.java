module org.kore.cash.register.fx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.kore.cash.register.fx to javafx.fxml;
    exports org.kore.cash.register.fx;
}
