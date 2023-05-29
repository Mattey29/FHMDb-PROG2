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

public class WatchlistDao{

    private Dao<WatchlistMovieEntity, Long> dao;
    private Database database;
    public WatchlistDao() throws DatabaseException {
        database = Database.getInstance();
        ConnectionSource connectionSource = database.getConnectionSource();
        try{
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e){
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", "Error creating Watchlist DAO.");
        }
    }

    public boolean addToWatchlist(Movie movie) throws DatabaseException {
        WatchlistMovieEntity entity = new WatchlistMovieEntity(movie);
        if(!watchList.contains(movie)){
            watchList.add(movie);
            try{
                dao.create(entity);
            } catch(SQLException e) {
                MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", "Error creating Watchlist record in db.");
            }
            return true;
        }
        return false;
    }

    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        WatchlistMovieEntity entity = new WatchlistMovieEntity(movie);
        watchList.remove(movie);
        try{
            DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
            Where<WatchlistMovieEntity, Long> where = deleteBuilder.where();
            where.eq("apiId", entity.apiId);  // Add your WHERE condition here
            deleteBuilder.delete();
        } catch(SQLException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", "Error deleting Watchlist record in db.");
        }
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try{
            /*for (WatchlistMovieEntity movie : list) {
                String movieString = movie.toString();
                System.out.println(movieString);
            }*/
            return dao.queryForAll();
        } catch(SQLException e) {
            MovieAlert.showAlert(Alert.AlertType.ERROR,"FEHLER","", "Error retrieving all Watchlist records.");
        }
        return new ArrayList<>();
    }
}