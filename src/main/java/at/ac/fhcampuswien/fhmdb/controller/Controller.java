package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.Observer;
import at.ac.fhcampuswien.fhmdb.data.Database;
import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import javafx.scene.control.Alert;

import java.sql.SQLException;

/**
 * Die Controller-Klasse verwaltet die Logik und fungiert als Beobachter für Aktualisierungen im Watchlist-Repository.
 * Sie stellt Click-Event-Handler zum Hinzufügen und Entfernen von Filmen aus der Watchlist bereit.
 */
public class Controller implements Observer {
    @Override
    public void update(String message) {
        MovieAlert.showAlert(Alert.AlertType.INFORMATION, "Hinweis", "", message);
    }

    /**
     * Eine funktionale Schnittstelle, die einen Click-Event-Handler definiert.
     */
    public interface ClickEventHandler<T> {
        /**
         * Verarbeitet das Click-Event für das angegebene Element.
         */
        void onClick(T t);
    }

    private final WatchlistRepository watchlistRepository = WatchlistRepository.getInstance();

    /**
     * Erzeugt ein neues Controller-Objekt.
     */
    public Controller() throws SQLException, DatabaseException {
        Database database = Database.getInstance();
        this.watchlistRepository.addObserver(this);
    }

    private final ClickEventHandler<Movie> onAddToWatchlistClicked = (clickedItem) -> {
        try {
            watchlistRepository.addToWatchlist(clickedItem);
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", e.getMessage());
        }
    };

    private final ClickEventHandler<Movie> onRemoveFromWatchlistClicked = (clickedItem) -> {
        try {
            watchlistRepository.removeFromWatchlist(clickedItem);
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", e.getMessage());
        }
    };

    /**
     * Gibt den Click-Event-Handler zum Hinzufügen eines Films zur Watchlist zurück.
     */
    public ClickEventHandler<Movie> getOnAddToWatchlistClicked() {
        return onAddToWatchlistClicked;
    }

    /**
     * Gibt den Click-Event-Handler zum Entfernen eines Films aus der Watchlist zurück.
     */
    public ClickEventHandler<Movie> getOnRemoveFromWatchlistClicked() {
        return onRemoveFromWatchlistClicked;
    }
}
