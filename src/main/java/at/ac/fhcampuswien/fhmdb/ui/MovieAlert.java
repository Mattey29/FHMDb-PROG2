package at.ac.fhcampuswien.fhmdb.ui;

import javafx.scene.control.Alert;

/**
 * Eine Hilfsklasse zum Anzeigen von Alert-Fenstern.
 */
public class MovieAlert {
    /**
     * Zeigt einen Alert mit dem angegebenen Typ, Titel, Header und Text an.
     */
    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
