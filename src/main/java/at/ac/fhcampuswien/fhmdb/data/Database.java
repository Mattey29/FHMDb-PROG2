package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public final String DB_URL = "jdbc:h2:file:./watchlist";
    static final String JDBC_DRIVER = "org.h2.Driver";
    private final String USER = "sa";
    private final String PASSWORD = "";
    public ConnectionSource connectionSource;
    Dao<WatchlistMovieEntity, Long> dao;

    public Database() throws DatabaseException {
        createConnectionSource();
        createTables();
    }

    private void createConnectionSource() throws DatabaseException {
        try {
            this.connectionSource = new JdbcConnectionSource(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseException("Error creating ConnectionSource", e);
        }
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public void createTables() throws DatabaseException {
        try{
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch(SQLException e){
            throw new DatabaseException("Error creating Table", e);
        }
    }
}