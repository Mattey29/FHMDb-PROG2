package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    // Database URL for H2 local file database
    public final String DB_URL = "jdbc:h2:file:./watchlist";
    // JDBC driver class for H2 database
    static final String JDBC_DRIVER = "org.h2.Driver";
    // Username for database
    private final String USER = "sa";
    // Password for database
    private final String PASSWORD = "";
    // ConnectionSource object to manage database connections
    public ConnectionSource connectionSource;
    // Data access object for WatchlistMovieEntity
    Dao<WatchlistMovieEntity, Long> dao;

    // Constructor
    public Database() throws DatabaseException {
        // Create a connection source and create tables
        createConnectionSource();
        createTables();
    }

    // Method to create a connection source to the database
    private void createConnectionSource() throws DatabaseException {
        try {
            // Initialize connection source with database URL, username, and password
            this.connectionSource = new JdbcConnectionSource(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Handle SQLException and throw custom DatabaseException
            throw new DatabaseException("Error creating ConnectionSource", e);
        }
    }

    // Getter for the ConnectionSource object
    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    // Method to create tables if they do not already exist
    public void createTables() throws DatabaseException {
        try {
            // Create table for WatchlistMovieEntity if it does not exist
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e) {
            // Handle SQLException and throw custom DatabaseException
            throw new DatabaseException("Error creating Table", e);
        }
    }
}
