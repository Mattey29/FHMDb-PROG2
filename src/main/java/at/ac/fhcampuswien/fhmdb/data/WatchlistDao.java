package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class WatchlistDao{

    private Dao<WatchlistMovieEntity, Long> dao;
    private Database database;
    public WatchlistDao() throws DatabaseException {
        database = new Database();
        ConnectionSource connectionSource = database.getConnectionSource();
        try{
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e){
            throw new DatabaseException("Error creating Watchlist DAO.",e);
        }
    }

    public void addToWatchlist(Movie movie) throws DatabaseException {
        WatchlistMovieEntity entity = new WatchlistMovieEntity(movie);
        try{
            dao.create(entity);
        } catch(SQLException e) {
            throw new DatabaseException("Error creating Watchlist record in db.", e);
        }
    }

    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        WatchlistMovieEntity entity = new WatchlistMovieEntity(movie);
        try{
            dao.delete(entity);
        } catch(SQLException e) {
            throw new DatabaseException("Error deleting Watchlist record in db.", e);
        }
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try{
            return dao.queryForAll();
        } catch(SQLException e) {
            throw new DatabaseException("Error retrieving all Watchlist records.", e);
        }
    }
}