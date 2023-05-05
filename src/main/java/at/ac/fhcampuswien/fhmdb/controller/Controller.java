package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.data.Database;
import at.ac.fhcampuswien.fhmdb.data.WatchlistDao;
import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.sql.SQLException;



public class Controller {
    public interface ClickEventHandler<T> {
        void onClick(T t);
    }

    private WatchlistDao watchlistDao;
    private Database database;

    public Controller() throws SQLException, DatabaseException {
        database = new Database();
        watchlistDao = new WatchlistDao();
    }

    private final ClickEventHandler<Movie> onAddToWatchlistClicked = (clickedItem) -> {
        try {
            watchlistDao.addToWatchlist(clickedItem);
        } catch (DatabaseException e) {
            // Show error message to the user in the UI
        }
    };

    private final ClickEventHandler<Movie> onRemoveFromWatchlistClicked = (clickedItem) -> {
        try {
            watchlistDao.removeFromWatchlist(clickedItem);
        } catch (DatabaseException e) {
            // Show error message to the user in the UI
        }
    };

    public ClickEventHandler<Movie> getOnAddToWatchlistClicked() {
        return onAddToWatchlistClicked;
    }

    public ClickEventHandler<Movie> getOnRemoveFromWatchlistClicked() {
        return onRemoveFromWatchlistClicked;
    }
}
