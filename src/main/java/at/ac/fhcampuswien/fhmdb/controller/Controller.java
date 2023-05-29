package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.data.Database;
import at.ac.fhcampuswien.fhmdb.data.WatchlistDao;
import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class Controller {
    public interface ClickEventHandler<T> {
        void onClick(T t);
    }

    private WatchlistDao watchlistDao;
    private Database database;

    public Controller() throws SQLException, DatabaseException {
        database = Database.getInstance();
        watchlistDao = new WatchlistDao();
    }

    private final ClickEventHandler<Movie> onAddToWatchlistClicked = (clickedItem) -> {
        try {
            watchlistDao.addToWatchlist(clickedItem);
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", e.getMessage());
        }
    };

    private final ClickEventHandler<Movie> onRemoveFromWatchlistClicked = (clickedItem) -> {
        try {
            watchlistDao.removeFromWatchlist(clickedItem);
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
