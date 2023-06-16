package at.ac.fhcampuswien.fhmdb.exception;

/**
 * Die Klasse MovieApiException repräsentiert eine benutzerdefinierte Laufzeit-Ausnahme, die bei Fehlern in der Movie-API auftritt.
 */
public class MovieApiException extends RuntimeException {
    /**
     * Konstruktor für eine MovieApiException mit einer Fehlermeldung.
     */
    public MovieApiException(String message) {
        super(message);
    }

    /**
     * Konstruktor für eine MovieApiException mit einer Fehlermeldung und einer Ursache.
     */
    public MovieApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
