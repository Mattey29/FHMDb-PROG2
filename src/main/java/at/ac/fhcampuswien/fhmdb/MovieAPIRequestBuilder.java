package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MovieAPIRequestBuilder {
    private final StringBuilder urlBuilder;

    public MovieAPIRequestBuilder(String base) {
        this.urlBuilder = new StringBuilder(base);
    }

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

    public String build() {
        return urlBuilder.toString();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
