package at.ac.fhcampuswien.fhmdb.exception;

/**
 * Die Klasse DatabaseException repräsentiert eine benutzerdefinierte Ausnahme, die bei Fehlern mit der Datenbank auftritt.
 */
public class DatabaseException extends Exception {

    /**
     * Konstruktor für eine DatabaseException mit einer Fehlermeldung.
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Konstruktor für eine DatabaseException mit einer Fehlermeldung und einer Ursache.
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
