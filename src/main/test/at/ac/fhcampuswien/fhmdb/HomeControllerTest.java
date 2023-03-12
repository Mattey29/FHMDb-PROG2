package at.ac.fhcampuswien.fhmdb;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
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

    }



}