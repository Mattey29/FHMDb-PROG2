package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.MovieAPI;

import java.io.IOException;
import java.util.*;

/**
 * Die Klasse Movie repräsentiert einen Film mit verschiedenen Eigenschaften wie Titel, Beschreibung, Genres, Erscheinungsjahr usw.
 */
public class Movie {
    private String id; // ID des Films
    private String title; // Titel des Films
    private String description; // Beschreibung des Films
    private List<Genre> genres; // Liste der Genres des Films
    private int releaseYear; // Erscheinungsjahr des Films
    private String imgUrl; // URL des Filmplakats
    private int lengthInMinutes; // Länge des Films in Minuten
    private List<String> directors; // Liste der Regisseure des Films
    private List<String> writers; // Liste der Drehbuchautoren des Films
    private List<String> mainCast; // Liste der Hauptdarsteller des Films
    private double rating; // Bewertung des Films

    /**
     * Konstruktor, der alle Attribute des Films setzt
     */
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

    /**
     * Konstruktor, der einen Film anhand seiner ID von der MovieAPI lädt
     */
    public Movie(String id) throws IOException {
        MovieAPI api = new MovieAPI();
        Movie movie = api.getMovieById(id);

        this.id = movie.id;
        this.title = movie.title;
        this.description = movie.description;
        this.genres = movie.genres;
        this.releaseYear = movie.releaseYear;
        this.imgUrl = movie.imgUrl;
        this.lengthInMinutes = movie.lengthInMinutes;
        this.directors = movie.directors;
        this.writers = movie.writers;
        this.mainCast = movie.mainCast;
        this.rating = movie.rating;
    }

    /**
     * Konstruktor für die Erstellung eines Films mit Titel, Beschreibung und Genres
     */
    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    /**
     * Standardkonstruktor
     */
    public Movie() {
    }

    /**
     * Statische Methode zum Initialisieren einer Liste von Beispiel-Filmen
     */
    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();

        List<Genre> darkKnightGenres = Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA);
        movies.add(new Movie("The Dark Knight", "Batman kämpft gegen den Joker in Gotham City", darkKnightGenres));

        List<Genre> jurassicParkGenres = Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Jurassic Park", "Wissenschaftler klonen Dinosaurier mit katastrophalen Folgen", jurassicParkGenres));

        List<Genre> toyStoryGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.COMEDY);
        movies.add(new Movie("Toy Story", "Eine Cowboy-Puppe und ihre Freunde werden lebendig, wenn keine Menschen da sind", toyStoryGenres));

        List<Genre> avengersGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION);
        movies.add(new Movie("Avengers: Endgame", "Die Avengers versuchen, die durch Thanos verursachten Schäden rückgängig zu machen", avengersGenres));

        List<Genre> theLionKingGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.DRAMA);
        movies.add(new Movie("Der König der Löwen", "Ein Löwenprinz muss seine eigenen Dämonen überwinden, um König zu werden", theLionKingGenres));

        List<Genre> theSocialNetworkGenres = Arrays.asList(Genre.DRAMA);
        movies.add(new Movie("The Social Network", "Die Geschichte, wie Mark Zuckerberg Facebook geschaffen hat", theSocialNetworkGenres));

        List<Genre> inceptionGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Inception", "Ein Dieb stiehlt Unternehmensgeheimnisse mithilfe von Traumteilungstechnologie", inceptionGenres));

        List<Genre> forrestGumpGenres = Arrays.asList(Genre.COMEDY, Genre.DRAMA, Genre.ROMANCE);
        movies.add(new Movie("Forrest Gump", "Forrest Gump, ein Mann mit niedrigem IQ, vollbringt Großes in seinem Leben", forrestGumpGenres));

        List<Genre> theMartianGenres = Arrays.asList(Genre.ADVENTURE, Genre.DRAMA, Genre.SCIENCE_FICTION);
        movies.add(new Movie("Der Marsianer - Rettet Mark Watney", "Ein Astronaut wird fälschlicherweise für tot gehalten und auf dem Mars zurückgelassen", theMartianGenres));

        return movies;
    }

    /**
     * Getter für die ID des Films
     */
    public String getId() {
        return id;
    }

    /**
     * Setter für die ID des Films
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter für den Titel des Films
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter für den Titel des Films
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter für die Beschreibung des Films
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter für die Beschreibung des Films
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter für die Liste der Genres des Films
     */
    public List<Genre> getGenres() {
        // Wenn genres null ist, wird eine leere Liste zurückgegeben
        return Objects.requireNonNullElse(genres, Collections.emptyList());
    }

    /**
     * Setter für die Liste der Genres des Films
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Getter für das Erscheinungsjahr des Films
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Setter für das Erscheinungsjahr des Films
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Getter für die URL des Filmplakats
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Setter für die URL des Filmplakats
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Getter für die Länge des Films in Minuten
     */
    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    /**
     * Setter für die Länge des Films in Minuten
     */
    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    /**
     * Getter für die Liste der Regisseure des Films
     */
    public List<String> getDirectors() {
        return directors;
    }

    /**
     * Setter für die Liste der Regisseure des Films
     */
    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    /**
     * Getter für die Liste der Drehbuchautoren des Films
     */
    public List<String> getWriters() {
        return writers;
    }

    /**
     * Setter für die Liste der Drehbuchautoren des Films
     */
    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    /**
     * Getter für die Liste der Hauptdarsteller des Films
     */
    public List<String> getMainCast() {
        return mainCast;
    }

    /**
     * Setter für die Liste der Hauptdarsteller des Films
     */
    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }

    /**
     * Getter für die Bewertung des Films
     */
    public double getRating() {
        return rating;
    }

    /**
     * Setter für die Bewertung des Films
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Überschreibt die toString-Methode, um eine lesbare Darstellung des Films zu erhalten
     */
    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genres=" + genres +
                ", releaseYear=" + releaseYear +
                ", imgUrl='" + imgUrl + '\'' +
                ", lengthInMinutes=" + lengthInMinutes +
                ", directors=" + directors +
                ", writers=" + writers +
                ", mainCast=" + mainCast +
                ", rating=" + rating +
                '}';
    }
}
