package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.ui.MovieAlert;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static at.ac.fhcampuswien.fhmdb.HomeController.watchList;

/**
 * Die Klasse WatchlistDao ist für die Datenbankoperationen der Watchlist verantwortlich.
 */
public class WatchlistDao {

    private Dao<WatchlistMovieEntity, Long> dao;
    private Database database;

    /**
     * Konstruktor der Klasse WatchlistDao.
     */
    public WatchlistDao() throws DatabaseException {
        database = Database.getInstance();
        ConnectionSource connectionSource = database.getConnectionSource();
        try {
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", "Fehler beim Erstellen des Watchlist DAO.");
        }
    }

    /**
     * Fügt einen Film der Watchlist hinzu.
     */
    public boolean addToWatchlist(Movie movie) throws DatabaseException {
        WatchlistMovieEntity entity = new WatchlistMovieEntity(movie);
        if (!watchList.contains(movie)) {
            watchList.add(movie);
            try {
                dao.create(entity);
            } catch (SQLException e) {
                MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", "Fehler beim Erstellen des Watchlist-Eintrags in der Datenbank.");
            }
            return true;
        }
        return false;
    }

    /**
     * Entfernt einen Film aus der Watchlist.
     */
    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        WatchlistMovieEntity entity = new WatchlistMovieEntity(movie);
        watchList.remove(movie);
        try {
            DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
            Where<WatchlistMovieEntity, Long> where = deleteBuilder.where();
            where.eq("apiId", entity.apiId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR, "FEHLER", "", "Fehler beim Löschen des Watchlist-Eintrags in der Datenbank.");
        }
    }

    /**
     * Ruft alle Filme aus der Watchlist ab.
     */
    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try{
            return dao.queryForAll();
        } catch(SQLException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", "Error retrieving all Watchlist records.");
        }
        return new ArrayList<>();
    }
}