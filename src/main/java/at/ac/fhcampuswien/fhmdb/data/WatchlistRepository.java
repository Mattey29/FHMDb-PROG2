package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import javafx.scene.control.Alert;

import java.util.List;

public class WatchlistRepository {
    private WatchlistDao watchlistDao;
    private static WatchlistRepository instance;

    private WatchlistRepository()  {
        try{
            watchlistDao = new WatchlistDao();
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", e.getMessage());
        }
    }

    public static WatchlistRepository getInstance() {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
    }

    public void addToWatchlist(Movie movie) throws DatabaseException {
        watchlistDao.addToWatchlist(movie);
    }

    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        watchlistDao.removeFromWatchlist(movie);
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        return watchlistDao.getAll();
    }

}
