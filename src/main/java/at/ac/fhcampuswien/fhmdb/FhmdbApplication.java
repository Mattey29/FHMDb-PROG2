package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.ControllerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Die Hauptklasse der FHMDb-Anwendung.
 * Sie erstellt und zeigt das Hauptfenster der Anwendung an.
 */
public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Erstelle eine Instanz der ControllerFactory
        ControllerFactory factory = new ControllerFactory();

        // Lade das FXML-Dokument für die Home-Ansicht
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setControllerFactory(factory);

        // Erstelle eine Scene mit der geladenen FXML-Datei und einer festgelegten Größe
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);

        // Füge die CSS-Datei zum Scene-Stylesheet hinzu
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());

        // Setze den Titel des Hauptfensters
        stage.setTitle("FHMDb");

        // Setze die Scene im Hauptfenster und zeige es an
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Starte die JavaFX-Anwendung
        launch();
    }
}
