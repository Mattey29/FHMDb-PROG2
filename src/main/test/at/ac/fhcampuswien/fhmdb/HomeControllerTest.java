package at.ac.fhcampuswien.fhmdb;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HomeControllerTest {

    private static HomeController homeController;

    @BeforeAll
    static void init() {
        homeController = new HomeController();
    }

    @Test
    void movies_are_correctly_sorted_ascending() {
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
    void movies_are_correctly_sorted_descending() {
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
    void test_filtering_by_genre_one_hit(){

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

    }



}