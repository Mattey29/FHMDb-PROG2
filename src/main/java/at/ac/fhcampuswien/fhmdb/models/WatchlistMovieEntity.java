package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "watchlist_movies")
public class WatchlistMovieEntity extends Movie {
    @DatabaseField(generatedId = true)
    public long id;
    @DatabaseField
    public String apiId;
    @DatabaseField
    public String title;
    @DatabaseField
    public String description;
    @DatabaseField
    public String genres;
    @DatabaseField(columnName = "release_year")
    public int releaseYear;
    @DatabaseField(columnName = "img_url")
    public String imgUrl;
    @DatabaseField(columnName = "length_in_minutes")
    public int lengthInMinutes;
    @DatabaseField
    public double rating;

    /**
     * Konstruktor für die WatchlistMovieEntity-Klasse.
     */
    public WatchlistMovieEntity(String id, String title, String description, List<Genre> genres, int releaseYear, String imgUrl, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        super(id, title, description, genres, releaseYear, imgUrl, lengthInMinutes, directors, writers, mainCast, rating);
        this.apiId = id;
    }

    /**
     * Konstruktor für die WatchlistMovieEntity-Klasse.
     */
    public WatchlistMovieEntity(Movie movie) {
        this.apiId = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.genres = genresToString(movie.getGenres());
        this.releaseYear = movie.getReleaseYear();
        this.imgUrl = movie.getImgUrl();
        this.lengthInMinutes = movie.getLengthInMinutes();
        this.rating = movie.getRating();
    }

    /**
     * Standardkonstruktor für die WatchlistMovieEntity-Klasse.
     * Erforderlich für ORMLite.
     */
    public WatchlistMovieEntity() {
        super();
    }

    /**
     * Konvertiert eine Liste von WatchlistMovieEntity-Objekten in eine Liste von Movie-Objekten.
     */
    public static List<Movie> convertToMovieList(List<WatchlistMovieEntity> watchlist) throws IOException {
        List<Movie> movies = new ArrayList<>();
        for (WatchlistMovieEntity entity : watchlist) {
            Movie movie = new Movie(entity.apiId);
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Konvertiert das WatchlistMovieEntity-Objekt in ein Movie-Objekt.
     */
    public Movie toMovie() {
        return new Movie(getId(), getTitle(), getDescription(), getGenres(), getReleaseYear(),
                getImgUrl(), getLengthInMinutes(), getDirectors(), getWriters(), getMainCast(), getRating());
    }

    /**
     * Konvertiert eine Liste von Genres in einen String.
     */
    public String genresToString(List<Genre> genres) {
        StringBuilder sb = new StringBuilder();

        for (Genre genre : genres) {
            sb.append(genre.name());
            sb.append(", ");
        }

        // Entferne das abschließende Komma und Leerzeichen, falls Genres vorhanden sind
        if (!genres.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "WatchlistMovieEntity{" +
                "id=" + id +
                ", apiId='" + apiId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genres='" + genres + '\'' +
                ", releaseYear=" + releaseYear +
                ", imgUrl='" + imgUrl + '\'' +
                ", lengthInMinutes=" + lengthInMinutes +
                ", rating=" + rating +
                '}';
    }
}
