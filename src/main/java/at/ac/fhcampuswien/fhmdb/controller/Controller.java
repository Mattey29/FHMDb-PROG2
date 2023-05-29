package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.data.Database;
import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class Controller {
    public interface ClickEventHandler<T> {
        void onClick(T t);
    }

    private final WatchlistRepository watchlistRepository = WatchlistRepository.getInstance();

    public Controller() throws SQLException, DatabaseException {
        Database database = Database.getInstance();
    }

    private final ClickEventHandler<Movie> onAddToWatchlistClicked = (clickedItem) -> {
        try {
            watchlistRepository.addToWatchlist(clickedItem);
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", e.getMessage());
        }
    };

    private final ClickEventHandler<Movie> onRemoveFromWatchlistClicked = (clickedItem) -> {
        try {
            watchlistRepository.removeFromWatchlist(clickedItem);
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", e.getMessage());
        }
    };

    public ClickEventHandler<Movie> getOnAddToWatchlistClicked() {
        return onAddToWatchlistClicked;
    }

    public ClickEventHandler<Movie> getOnRemoveFromWatchlistClicked() {
        return onRemoveFromWatchlistClicked;
    }
}
