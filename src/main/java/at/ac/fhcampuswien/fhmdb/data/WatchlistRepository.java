package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.Observable;
import at.ac.fhcampuswien.fhmdb.Observer;
import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements Observable {
    private WatchlistDao watchlistDao;
    private static WatchlistRepository instance;
    private final List<Observer> observers;

    private WatchlistRepository()  {
        try{
            watchlistDao = new WatchlistDao();
        } catch (DatabaseException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", e.getMessage());
        }
        this.observers = new ArrayList<>();
    }

    public static WatchlistRepository getInstance() {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
    }

    public void addToWatchlist(Movie movie) throws DatabaseException {
        boolean added = watchlistDao.addToWatchlist(movie);
        if (added){
            notifyObservers("Movie has been added to Watchlist!");
        } else {
            notifyObservers("Movie is already on Watchlist");
        }
    }

    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        watchlistDao.removeFromWatchlist(movie);
        notifyObservers("Movie has been removed from Watchlist!");
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        return watchlistDao.getAll();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
