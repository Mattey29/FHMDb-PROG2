package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "watchlist_movies")
public class WatchlistMovieEntity extends Movie{
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

    public WatchlistMovieEntity(String id, String title, String description, List<Genre> genres, int releaseYear, String imgUrl, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        super(id, title, description, genres, releaseYear, imgUrl, lengthInMinutes, directors, writers, mainCast, rating);
        this.apiId = id;
    }

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
    public WatchlistMovieEntity() {
        super();
        // empty constructor required by ORMLite
    }

    public String genresToString(List<Genre> genres){
        StringBuilder sb = new StringBuilder();

        for (Genre genre : genres) {
            sb.append(genre.name());
            sb.append(", ");
        }

        // Remove the trailing comma and space if there are genres
        if (!genres.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.toString();
    }

    public Movie toMovie() {
        return new Movie(getId(), getTitle(), getDescription(), getGenres(), getReleaseYear(),
                getImgUrl(), getLengthInMinutes(), getDirectors(), getWriters(), getMainCast(), getRating());
    }

    public static List<Movie> convertToMovieList(List<WatchlistMovieEntity> watchlist) throws IOException {
        List<Movie> movies = new ArrayList<>();
        for (WatchlistMovieEntity entity : watchlist) {
            Movie movie = new Movie(entity.apiId);
            movies.add(movie);
        }
        return movies;
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