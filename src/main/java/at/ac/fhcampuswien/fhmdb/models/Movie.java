package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String id;
    private String title;
    private String description;
    private List<Genre> genres;
    private int releaseYear;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;
    private List<String> writers;
    private List<String> mainCast;
    private double rating;

    public Movie(String id, String title, String description, List<Genre> genres, int releaseYear, String imgUrl, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
    }

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();

        List<Genre> darkKnightGenres = Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA);
        movies.add(new Movie("The Dark Knight", "Batman battles the Joker in Gotham City", darkKnightGenres));

        List<Genre> jurassicParkGenres = Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Jurassic Park", "Scientists clone dinosaurs with disastrous consequences", jurassicParkGenres));

        List<Genre> toyStoryGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.COMEDY);
        movies.add(new Movie("Toy Story", "A cowboy doll and his friends come to life when humans aren't around", toyStoryGenres));

        List<Genre> avengersGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION);
        movies.add(new Movie("Avengers: Endgame", "The Avengers attempt to reverse the damage caused by Thanos", avengersGenres));

        List<Genre> theLionKingGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.DRAMA);
        movies.add(new Movie("The Lion King", "A lion prince must overcome his own demons to become king", theLionKingGenres));

        List<Genre> theSocialNetworkGenres = Arrays.asList(Genre.DRAMA);
        movies.add(new Movie("The Social Network", "The story of how Mark Zuckerberg created Facebook", theSocialNetworkGenres));

        List<Genre> inceptionGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Inception", "A thief steals corporate secrets through the use of dream-sharing technology", inceptionGenres));

        List<Genre> forrestGumpGenres = Arrays.asList(Genre.COMEDY, Genre.DRAMA, Genre.ROMANCE);
        movies.add(new Movie("Forrest Gump", "Forrest Gump, a man with a low IQ, accomplishes great things in his life", forrestGumpGenres));

        List<Genre> theMartianGenres = Arrays.asList(Genre.ADVENTURE, Genre.DRAMA, Genre.SCIENCE_FICTION);
        movies.add(new Movie("The Martian", "An astronaut is mistakenly presumed dead and left behind on Mars", theMartianGenres));

        return movies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
