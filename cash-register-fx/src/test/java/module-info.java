open    module org.kore.cashregister.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires org.mockito;
    requires net.bytebuddy; //needed by mockito
    requires org.kore.cashregister;
}
