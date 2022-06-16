package org.kore.cashregister;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/images/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        String storageFolder = System.getProperty("storageFolder");
        if (storageFolder == null) {
            storageFolder = System.getProperty("user.home");
            storageFolder = Paths.get(storageFolder, "cash-register-fx").toAbsolutePath().toString();
            System.setProperty("storageFolder", storageFolder);
        }
        launch();
    }

}