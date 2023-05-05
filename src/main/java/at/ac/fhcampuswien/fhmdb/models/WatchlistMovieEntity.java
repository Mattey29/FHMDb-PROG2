package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
        super(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getGenres(), movie.getReleaseYear(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getDirectors(), movie.getWriters(), movie.getMainCast(), movie.getRating());
        this.apiId = String.valueOf(id);
    }
    public WatchlistMovieEntity() {
        super();
        // empty constructor required by ORMLite
    }

    public String genresToString(List<Genre> genres){
        return "";
    }

}