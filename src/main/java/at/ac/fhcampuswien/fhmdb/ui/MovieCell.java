package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.controller.Controller;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Eine benutzerdefinierte Zelle für die Anzeige von Filminformationen in einer ListView.
 * Die Zelle enthält Titel, Details, Genres und optionale Schaltflächen zum Hinzufügen und Entfernen von Filmen zur Watchlist.
 */
public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genres = new Label();
    private final VBox layout = new VBox();
    private final JFXButton addToWatchlistBtn = new JFXButton("Zur Watchlist hinzufügen");
    private final JFXButton removeFromWatchlistBtn = new JFXButton("Aus Watchlist entfernen");
    private final HBox buttons = new HBox(addToWatchlistBtn, removeFromWatchlistBtn);

    public MovieCell(Controller.ClickEventHandler<Movie> addToWatchlistClicked, Controller.ClickEventHandler<Movie> removeFromWatchlistClicked, boolean isHomeView, HomeController homeController) {
        getStylesheets().add(getClass().getResource("/at/ac/fhcampuswien/fhmdb/styles.css").toExternalForm());

        // Füge den Schaltflächen Stil-Klassen hinzu
        addToWatchlistBtn.getStyleClass().addAll("background-yellow", "text-white");
        removeFromWatchlistBtn.getStyleClass().addAll("background-yellow", "text-white");

        addToWatchlistBtn.setOnMouseClicked(mouseEvent -> {
            addToWatchlistClicked.onClick(getItem());
        });

        removeFromWatchlistBtn.setOnMouseClicked(mouseEvent -> {
            removeFromWatchlistClicked.onClick(getItem());
            homeController.updateListView(HomeController.watchList, false);
        });

        // Zeige die Schaltflächen basierend auf dem Ansichtstyp an oder aus
        if (isHomeView) {
            buttons.getChildren().remove(removeFromWatchlistBtn);
        } else {
            buttons.getChildren().remove(addToWatchlistBtn);
        }

        layout.getChildren().addAll(title, detail, genres, buttons);
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
            setText(null);
        } else {
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "Keine Beschreibung verfügbar"
            );

            // Setze den Text für das Genres Label
            StringBuilder genresText = new StringBuilder();
            for (int i = 0; i < movie.getGenres().size(); i++) {
                genresText.append(movie.getGenres().get(i).toString());
                if (i < movie.getGenres().size() - 1) {
                    genresText.append(", ");
                }
            }
            genres.setText(genresText.toString().replace("_", " ")); // Ersetze Unterstriche in Genrenamen (z.B. SCIENCE_FICTION zu SCIENCE FICTION)

            // Farbschema
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genres.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // Layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.setAlignment(Pos.CENTER_LEFT);
            buttons.setAlignment(Pos.CENTER_RIGHT);
            buttons.setSpacing(10);

            setGraphic(layout);
        }
    }
}
