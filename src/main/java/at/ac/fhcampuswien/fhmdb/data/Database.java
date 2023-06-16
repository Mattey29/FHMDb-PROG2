package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Die Klasse Database verwaltet die Datenbankverbindung und die Erstellung der Tabellen.
 * Sie implementiert das Singleton-Muster, um sicherzustellen, dass nur eine Instanz der Datenbank vorhanden ist.
 */
public class Database {
    // Datenbank-URL für die H2-Dateidatenbank
    public final String DB_URL = "jdbc:h2:file:./watchlist";
    // JDBC-Treiberklasse für die H2-Datenbank
    static final String JDBC_DRIVER = "org.h2.Driver";
    // Benutzername für die Datenbank
    private final String USER = "sa";
    // Passwort für die Datenbank
    private final String PASSWORD = "";
    // ConnectionSource-Objekt zum Verwalten der Datenbankverbindungen
    private ConnectionSource connectionSource;
    // Datenzugriffsobjekt für WatchlistMovieEntity
    private Dao<WatchlistMovieEntity, Long> dao;

    private static Database instance;

    private Database() throws DatabaseException {
        // Erzeuge eine ConnectionSource und erstelle die Tabellen
        createConnectionSource();
        createTables();
    }

    // Öffentliche statische Methode zum Abrufen der Singleton-Instanz
    public static Database getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Methode zum Erstellen einer ConnectionSource zur Datenbank
    private void createConnectionSource() throws DatabaseException {
        try {
            // Initialisiere die ConnectionSource mit Datenbank-URL, Benutzername und Passwort
            this.connectionSource = new JdbcConnectionSource(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Behandle SQLException und werfe eine benutzerdefinierte DatabaseException
            throw new DatabaseException("Fehler beim Erstellen der ConnectionSource", e);
        }
    }

    // Getter für das ConnectionSource-Objekt
    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    // Methode zum Erstellen der Tabellen, falls sie noch nicht vorhanden sind
    public void createTables() throws DatabaseException {
        try {
            // Erstelle die Tabelle für WatchlistMovieEntity, falls sie nicht vorhanden ist
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e) {
            // Behandle SQLException und werfe eine benutzerdefinierte DatabaseException
            throw new DatabaseException("Fehler beim Erstellen der Tabelle", e);
        }
    }
}
