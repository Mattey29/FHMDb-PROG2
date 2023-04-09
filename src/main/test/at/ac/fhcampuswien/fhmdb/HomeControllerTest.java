package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    public static HomeController homeController;
    public static List<Movie> requestedMovies;
    public static MovieAPI movieAPI1;

    @BeforeAll
    public static void init() throws IOException {
        homeController = new HomeController();
        movieAPI1 = new MovieAPI();
        requestedMovies = movieAPI1.getMovies(null, null, null, null);
    }

    @Test
    public void test_movies_are_correctly_sorted_ascending() {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

        //given
        List<Movie> movies = new ArrayList<>();

        List<Genre> darkKnightGenres = Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA);
        movies.add(new Movie("The Dark Knight", "Batman battles the Joker in Gotham City", darkKnightGenres));

        List<Genre> jurassicParkGenres = Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Jurassic Park", "Scientists clone dinosaurs with disastrous consequences", jurassicParkGenres));

        List<Genre> toyStoryGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.COMEDY);
        movies.add(new Movie("Toy Story", "A cowboy doll and his friends come to life when humans aren't around", toyStoryGenres));

        List<Genre> avengersGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION);
        movies.add(new Movie("Avengers: Endgame", "The Avengers attempt to reverse the damage caused by Thanos", avengersGenres));

        observableMovies.addAll(movies);

        //when
        homeController.sortList(observableMovies, true); //true == ascending

        //then
        assertEquals("Avengers: Endgame", observableMovies.get(0).getTitle());
        assertEquals("Jurassic Park", observableMovies.get(1).getTitle());
        assertEquals("The Dark Knight", observableMovies.get(2).getTitle());
        assertEquals("Toy Story", observableMovies.get(3).getTitle());

    }

    @Test
    public void test_movies_are_correctly_sorted_descending() {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

        //given
        List<Movie> movies = new ArrayList<>();

        List<Genre> darkKnightGenres = Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA);
        movies.add(new Movie("The Dark Knight", "Batman battles the Joker in Gotham City", darkKnightGenres));

        List<Genre> jurassicParkGenres = Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Jurassic Park", "Scientists clone dinosaurs with disastrous consequences", jurassicParkGenres));

        List<Genre> toyStoryGenres = Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.COMEDY);
        movies.add(new Movie("Toy Story", "A cowboy doll and his friends come to life when humans aren't around", toyStoryGenres));

        List<Genre> avengersGenres = Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.SCIENCE_FICTION);
        movies.add(new Movie("Avengers: Endgame", "The Avengers attempt to reverse the damage caused by Thanos", avengersGenres));

        observableMovies.addAll(movies);

        //when
        homeController.sortList(observableMovies, false); //false == descending

        //then
        assertEquals("Avengers: Endgame", observableMovies.get(3).getTitle());
        assertEquals("Jurassic Park", observableMovies.get(2).getTitle());
        assertEquals("The Dark Knight", observableMovies.get(1).getTitle());
        assertEquals("Toy Story", observableMovies.get(0).getTitle());

    }

    @Test
    public void test_filtering_by_genre_one_hit(){

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

        //given
        List<Movie> movies = new ArrayList<>();

        List<Genre> darkKnightGenres = Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA);
        movies.add(new Movie("The Dark Knight", "Batman battles the Joker in Gotham City", darkKnightGenres));

        List<Genre> jurassicParkGenres = Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Jurassic Park", "Scientists clone dinosaurs with disastrous consequences", jurassicParkGenres));

        observableMovies.addAll(movies);

        Genre selectedGenre = Genre.ADVENTURE;
        String searchTerm = "";

        //when
        homeController.filterMovies(selectedGenre, searchTerm, observableMovies);


        //then
        assertEquals("Jurassic Park", observableMovies.get(0).getTitle());

    }

    @Test
    void getMostPopularActor() {
        //given
        String expectedResult = "Tom Hanks";

        //when
        String actualResult = homeController.getMostPopularActor(requestedMovies);

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getLongestMovieTitle() {
        //given
        int expectedResult = 46;

        //when
        int actualResult = homeController.getLongestMovieTitle(requestedMovies);

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countMoviesFrom() {
        //given
        double expectedResult = 3.0;

        //when
        double actualResult = homeController.countMoviesFrom(requestedMovies, "Steven Spielberg");

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getMoviesBetweenYears() {
        //given
        String expectedResult = "Goodfellas";

        //when
        List<Movie> actualResultMovie = homeController.getMoviesBetweenYears(requestedMovies, 1990, 1990);
        String actualResult = "";

        for (Movie movie : actualResultMovie) {
            actualResult = movie.getTitle();
        }

        //then
        assertEquals(expectedResult, actualResult);
    }



   /* @Test
    void test_filtering_by_genre_no_hit(){

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

        //given
        List<Movie> movies = new ArrayList<>();

        List<Genre> darkKnightGenres = Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA);
        movies.add(new Movie("The Dark Knight", "Batman battles the Joker in Gotham City", darkKnightGenres));

        List<Genre> jurassicParkGenres = Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER);
        movies.add(new Movie("Jurassic Park", "Scientists clone dinosaurs with disastrous consequences", jurassicParkGenres));

        observableMovies.addAll(movies);

        Genre selectedGenre = null;
        String searchTerm = "";

        //when
        homeController.filterMovies(selectedGenre, searchTerm, observableMovies);


        //then
        assertEquals(null, observableMovies.get(0).getTitle());

    }*/



}