package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MovieAPIRequestBuilder {
    private final StringBuilder urlBuilder;

    public MovieAPIRequestBuilder(String base) {
        this.urlBuilder = new StringBuilder(base);
    }

    /**
     * Fügt den Suchbegriff zur URL hinzu.
     */
    public MovieAPIRequestBuilder query(String lookupQuery) {
        if (lookupQuery != null && !lookupQuery.isEmpty()) {
            if (urlBuilder.toString().contains("?")) {
                urlBuilder.append("&query=").append(encode(lookupQuery));
            } else {
                urlBuilder.append("?query=").append(encode(lookupQuery));
            }
        }
        return this;
    }

    /**
     * Fügt das Genre zur URL hinzu.
     */
    public MovieAPIRequestBuilder genre(Genre lookupGenre) {
        if (lookupGenre != null) {
            if (urlBuilder.toString().contains("?")) {
                urlBuilder.append("&genre=").append(lookupGenre.name());
            } else {
                urlBuilder.append("?genre=").append(lookupGenre.name());
            }
        }
        return this;
    }

    /**
     * Fügt das Erscheinungsjahr zur URL hinzu.
     */
    public MovieAPIRequestBuilder releaseYear(Integer lookupReleaseYear) {
        if (lookupReleaseYear != null) {
            if (urlBuilder.toString().contains("?")) {
                urlBuilder.append("&releaseYear=").append(lookupReleaseYear);
            } else {
                urlBuilder.append("?releaseYear=").append(lookupReleaseYear);
            }
        }
        return this;
    }

    /**
     * Fügt die Mindestbewertung zur URL hinzu.
     */
    public MovieAPIRequestBuilder ratingFrom(Double lookupRatingFrom) {
        if (lookupRatingFrom != null) {
            if (urlBuilder.toString().contains("?")) {
                urlBuilder.append("&ratingFrom=").append(lookupRatingFrom);
            } else {
                urlBuilder.append("?ratingFrom=").append(lookupRatingFrom);
            }
        }
        return this;
    }

    /**
     * Erstellt die vollständige URL.
     */
    public String build() {
        return urlBuilder.toString();
    }

    /**
     * URL-encodiert den übergebenen Wert.
     */
    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
