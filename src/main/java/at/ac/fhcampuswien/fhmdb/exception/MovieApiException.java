package at.ac.fhcampuswien.fhmdb.exception;

import java.io.IOException;

public class MovieApiException extends RuntimeException {
    public MovieApiException(String message) {
        super(message);
    }

    public MovieApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
